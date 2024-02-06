package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
