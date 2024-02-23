package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.entity.RepairShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepairShopRepository extends JpaRepository<RepairShop, Long>, RepairShopRepositoryCustom {
    Optional<RepairShop> findRepairShopByShopName(String shopName);
}
