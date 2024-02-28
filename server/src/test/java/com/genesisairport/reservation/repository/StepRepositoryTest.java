package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.TestJPAQueryFactoryConfiguration;
import com.genesisairport.reservation.entity.Reservation;
import com.genesisairport.reservation.entity.Step;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import(TestJPAQueryFactoryConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class StepRepositoryTest {

    @Autowired
    private StepRepository stepRepository;

    @Test
    void findStepById_WhenExists_ShouldReturnStep() {
        Reservation reservation = Reservation.builder().id(1L).build();
        Step step = new Step();
        step.setReservation(reservation);
        step.setStage("정비중");
        step.setDate(LocalDateTime.now());
        Step savedStep = stepRepository.save(step);

        Step foundStep = stepRepository.findStepById(savedStep.getId());

        assertNotNull(foundStep);
    }

    @Test
    void findStagesByReservationId_WhenExists_ShouldReturnStages() {
        Reservation reservation = Reservation.builder().id(1L).build();
        Step step = new Step();
        step.setReservation(reservation);
        step.setStage("정비중");
        step.setDate(LocalDateTime.now());
        Step savedStep = stepRepository.save(step);

        List<String> stages = stepRepository.findStagesByReservationId(reservation.getId());

        assertFalse(stages.isEmpty());
    }
}
