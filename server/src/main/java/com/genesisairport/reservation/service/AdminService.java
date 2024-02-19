package com.genesisairport.reservation.service;

import com.genesisairport.reservation.entity.Step;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.respository.ReservationRepository;
import com.genesisairport.reservation.respository.StepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

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
        stepRepository.save(newStep);
    }
}
