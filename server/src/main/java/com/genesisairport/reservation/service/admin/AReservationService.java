package com.genesisairport.reservation.service.admin;

import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import com.genesisairport.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AReservationService {
    private final ReservationRepository reservationRepository;

    public List<AdminResponse.ReservationDetail> getAllReservations(AdminRequest.ReservationDetail requestBody) {
        return reservationRepository.findReservations(requestBody);
    }
}
