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

    public List<AdminResponse.ReservationDetail> getAllReservations(
            String shopName, String startPickUpDateTime, String endPickUpDateTime, String startReturnDateTime,
            String endReturnDateTime, String customerName, String sellName, String stage,
            String sortColumn, String sortDirection, String pageSize, String pageNumber
    ) {
        return reservationRepository.findReservations(
                shopName, startPickUpDateTime, endPickUpDateTime, startReturnDateTime,
                endReturnDateTime, customerName, sellName, stage, sortColumn, sortDirection,
                pageSize, pageNumber
        );
    }

    public AdminResponse.UploadImage addMaintenanceImage(Long reservationId, Integer status, String imageUrl, String objectKey) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isEmpty())
            throw new GeneralException(ResponseCode.BAD_REQUEST, "존재하지 않는 예약 id입니다.");

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
            throw new GeneralException(ResponseCode.BAD_REQUEST, "존재하지 않는 이미지 id입니다.");
        return image.get();
    }

    public void deleteMaintenanceImage(Long imageId) {
        Optional<MaintenanceImage> image = maintenanceImageRepository.findById(imageId);
        if (image.isEmpty())
            throw new GeneralException(ResponseCode.BAD_REQUEST, "존재하지 않는 이미지 id입니다.");
        maintenanceImageRepository.deleteById(imageId);
    }

    public Long saveStage(AdminRequest.StageInfo requestBody) {
        Step newStep = Step.builder()
                .stage(requestBody.getProgress().getName())
                .date(LocalDateTime.now())
                .detail(requestBody.getDetail())
                .createDatetime(LocalDateTime.now())
                .updateDatetime(LocalDateTime.now())
                .reservation(reservationRepository.findReservationById(requestBody.getReservationId()))
                .build();
        try {
            Step insertResult = stepRepository.save(newStep);
            setLatestStage(requestBody.getReservationId());
            return insertResult.getId();
        } catch (Exception e) {
            throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "이미 추가된 진행 단계입니다.");
        }
    }

    @Transactional
    public void deleteStage(long stepId) {
        long reservationId = stepRepository.findStepById(stepId).getReservation().getId();
        stepRepository.deleteById(stepId);

        setLatestStage(reservationId);
    }

    private void setLatestStage(long reservationId) {
        // 예약 현황 갱신
        Reservation reservation = reservationRepository.findReservationById(reservationId);

        List<ProgressStage> stages = stepRepository.findStagesByReservationId(reservationId).stream()
                .map(ProgressStage::fromName)
                .toList();

        reservation.setProgressStage(Objects.requireNonNull(stages.stream()
                        .max(Enum::compareTo)
                        .orElse(null))
                .getName()
        );

        reservationRepository.save(reservation);
    }

    public void updateComment(AdminRequest.CommentInfo requestBody) {
        try {
            Reservation reservation = reservationRepository.findReservationById(requestBody.getReservationId());
            try {
                reservation.setInspectionResult(requestBody.getComment());
                reservationRepository.save(reservation);
            } catch (Exception e) {
                throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "코멘트를 저장하는 데 실패했습니다.");
            }
        } catch (Exception e) {
            throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "예약 정보를 불러오는 데 실패했습니다.");
        }
    }
}
