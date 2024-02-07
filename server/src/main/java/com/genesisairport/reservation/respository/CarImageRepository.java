package com.genesisairport.reservation.respository;

import com.genesisairport.reservation.entity.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {

    public Optional<CarImage> findBySellName(String sellName);
}
