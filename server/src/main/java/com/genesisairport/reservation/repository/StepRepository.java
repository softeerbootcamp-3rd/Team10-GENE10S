package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {

    Step findStepByReservationIdAndStage(
            @Param("reservationId") Long reservation,
            @Param("stage") String progress);

    Step findStepById(long stepId);

    @Query("SELECT s.stage FROM Step s WHERE s.reservation.id = :reservationId")
    List<String> findStagesByReservationId(@Param("reservationId") Long reservationId);
}
