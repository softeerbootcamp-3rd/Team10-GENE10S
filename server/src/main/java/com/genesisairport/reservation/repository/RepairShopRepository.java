package com.genesisairport.reservation.repository;

import com.genesisairport.reservation.entity.RepairShop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepairShopRepository extends JpaRepository<RepairShop, Long>, RepairShopRepositoryCustom {
    RepairShop findRepairShopByShopName(String shopName);
}
