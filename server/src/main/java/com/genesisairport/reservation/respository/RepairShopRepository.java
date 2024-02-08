package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.entity.RepairShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RepairShopRepository extends JpaRepository<RepairShop, Long>, RepairShopRepositoryCustom {
    RepairShop findRepairShopByShopName(String shopName);

}
