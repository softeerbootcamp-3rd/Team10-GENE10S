package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {

    Step findStepByReservationIdAndStage(
            @Param("reservationId") Long reservation,
            @Param("stage") String progress);
}
