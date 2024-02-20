package com.genesisairport.reservation.service.admin;

import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.enums.ProgressStage;
import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.entity.Reservation;
import com.genesisairport.reservation.entity.Step;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.respository.ReservationRepository;
import com.genesisairport.reservation.respository.StepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminReservationService {

    private final ReservationRepository reservationRepository;
    private final StepRepository stepRepository;

    public void saveStage(AdminRequest.StageInfo requestBody) {
        Step newStep = Step.builder()
                .stage(requestBody.getProgress().getName())
                .date(LocalDateTime.now())
                .detail(requestBody.getDetail())
                .createDatetime(LocalDateTime.now())
                .updateDatetime(LocalDateTime.now())
                .reservation(reservationRepository.findReservationById(requestBody.getReservationId()))
                .build();
        try {
            stepRepository.save(newStep);
        } catch (Exception e) {
            throw new GeneralException(ResponseCode.INTERNAL_ERROR, "이미 추가된 진행 단계입니다.");
        }

        setLatestStage(requestBody);
    }

    @Transactional
    public void deleteStage(AdminRequest.StageInfo requestBody) {
        Long stepId = stepRepository.findStepByReservationIdAndStage(
                requestBody.getReservationId(),
                requestBody.getProgress().getName()).getId();

        stepRepository.deleteById(stepId);

        setLatestStage(requestBody);
    }

    private void setLatestStage(AdminRequest.StageInfo requestBody) {
        // 예약 현황 갱신
        Reservation reservation = reservationRepository.findReservationById(requestBody.getReservationId());

        List<ProgressStage> stages = stepRepository.findStagesByReservationId(requestBody.getReservationId()).stream()
                .map(ProgressStage::fromName)
                .collect(Collectors.toList());

        reservation.setProgressStage(stages.stream()
                .max(Enum::compareTo)
                .orElse(null)
                .getName()
        );

        reservationRepository.save(reservation);
    }
}
