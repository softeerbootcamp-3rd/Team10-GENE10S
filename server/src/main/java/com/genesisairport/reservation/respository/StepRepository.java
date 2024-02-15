package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {
}
