package com.genesisairport.reservation.service;

import com.genesisairport.reservation.entity.CarImage;
import com.genesisairport.reservation.respository.CarImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

    private final String DEFAULT_IMAGE = "https://test.test/";
    private final CarImageRepository carImageRepository;

    public String getDefaultImage() {
        return DEFAULT_IMAGE;
    }

    public String getCarImage(String sellName) {
        Optional<CarImage> carImage = carImageRepository.findBySellName(sellName);
        if (carImage.isPresent())
            return carImage.get().getImageUrl();
        return DEFAULT_IMAGE;
    }
}
