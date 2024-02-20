package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {
}
