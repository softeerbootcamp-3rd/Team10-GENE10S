package com.genesisairport.reservation.service.admin;

import com.genesisairport.reservation.common.enums.ProgressStage;
import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.entity.MaintenanceImage;
import com.genesisairport.reservation.entity.Reservation;
import com.genesisairport.reservation.entity.Step;

import com.genesisairport.reservation.repository.MaintenanceImageRepository;
import com.genesisairport.reservation.repository.StepRepository;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import com.genesisairport.reservation.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AReservationService {
    private final ReservationRepository reservationRepository;
    private final MaintenanceImageRepository maintenanceImageRepository;
    private final StepRepository stepRepository;

    public Page<AdminResponse.ReservationDetail> getAllReservations(AdminRequest.ReservationDetail reservationDetail, Pageable pageable) {
        return reservationRepository.findReservations(reservationDetail, pageable);
    }

    public AdminResponse.UploadImage addMaintenanceImage(Long reservationId, Integer status, String imageUrl, String objectKey) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isEmpty())
            throw new GeneralException(ResponseCode.NOT_FOUND, "예약 id를 찾을 수 없습니다.");

        MaintenanceImage entity = MaintenanceImage.builder()
                .reservation(reservation.get())
                .status(status)
                .imageUrl(imageUrl)
                .objectKey(objectKey)
                .build();

        MaintenanceImage insertResult = maintenanceImageRepository.save(entity);
        return AdminResponse.UploadImage.builder()
                .imageId(insertResult.getId())
                .imageUrl(insertResult.getImageUrl())
                .build();
    }

    public MaintenanceImage getMaintenanceImage(Long imageId) {
        Optional<MaintenanceImage> image = maintenanceImageRepository.findById(imageId);
        if (image.isEmpty())
            throw new GeneralException(ResponseCode.NOT_FOUND, "이미지 id를 찾을 수 없습니다.");
        return image.get();
    }

    public void deleteMaintenanceImage(Long imageId) {
        Optional<MaintenanceImage> image = maintenanceImageRepository.findById(imageId);
        if (image.isEmpty())
            throw new GeneralException(ResponseCode.NOT_FOUND, "이미지 id를 찾을 수 없습니다.");
        maintenanceImageRepository.deleteById(imageId);
    }

    @Transactional
    public Long saveStage(AdminRequest.StageInfo requestBody) {
        Optional<Reservation> reservation = reservationRepository.findById(requestBody.getReservationId());
        if (reservation.isEmpty())
            throw new GeneralException(ResponseCode.NOT_FOUND, "예약 id를 찾을 수 없습니다.");

        Step newStep = Step.builder()
                .stage(requestBody.getProgress().getName())
                .date(LocalDateTime.now())
                .detail(requestBody.getDetail())
                .createDatetime(LocalDateTime.now())
                .updateDatetime(LocalDateTime.now())
                .reservation(reservation.get())
                .build();
        Step insertResult = stepRepository.save(newStep);
        setLatestStage(requestBody.getReservationId());
        return insertResult.getId();
    }

    @Transactional
    public void deleteStage(long stepId) {
        long reservationId = stepRepository.findStepById(stepId).getReservation().getId();
        stepRepository.deleteById(stepId);

        setLatestStage(reservationId);
    }

    private void setLatestStage(long reservationId) {
        // 예약 현황 갱신
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isEmpty())
            throw new GeneralException(ResponseCode.NOT_FOUND, "예약 id를 찾을 수 없습니다.");

        List<ProgressStage> stages = stepRepository.findStagesByReservationId(reservationId).stream()
                .map(ProgressStage::fromName)
                .toList();

        reservation.get().setProgressStage(Objects.requireNonNull(stages.stream()
                        .max(Enum::compareTo)
                        .orElse(null))
                .getName()
        );

        reservationRepository.save(reservation.get());
    }

    public void updateComment(AdminRequest.CommentInfo requestBody) {
        Optional<Reservation> reservation = reservationRepository.findById(requestBody.getReservationId());
        if (reservation.isEmpty())
            throw new GeneralException(ResponseCode.NOT_FOUND, "예약 id를 찾을 수 없습니다.");
        reservation.get().setInspectionResult(requestBody.getComment());
        reservationRepository.save(reservation.get());
    }
}
