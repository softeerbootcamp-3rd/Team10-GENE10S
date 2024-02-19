package com.genesisairport.reservation.service.admin;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.entity.MaintenanceImage;
import com.genesisairport.reservation.entity.Reservation;
import com.genesisairport.reservation.respository.MaintenanceImageRepository;
import com.genesisairport.reservation.respository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminReservationService {
    private final ReservationRepository reservationRepository;
    private final MaintenanceImageRepository maintenanceImageRepository;

    public void addMaintenanceImage(Long reservationId, Integer status, String imageUrl) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isEmpty())
            throw new GeneralException(ResponseCode.BAD_REQUEST, "존재하지 않는 예약 id입니다.");

        MaintenanceImage entity = MaintenanceImage.builder()
                .reservation(reservation.get())
                .status(status)
                .imageUrl(imageUrl)
                .build();

        maintenanceImageRepository.save(entity);
    }
}
