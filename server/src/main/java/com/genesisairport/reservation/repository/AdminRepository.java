package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long>, AdminRepositoryCustom {

    Optional<Admin> findByAdminId(String adminId);

}
