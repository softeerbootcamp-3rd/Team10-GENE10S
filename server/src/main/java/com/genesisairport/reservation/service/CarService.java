package com.genesisairport.reservation.service;

import com.genesisairport.reservation.entity.Car;
import com.genesisairport.reservation.entity.CarImage;
import com.genesisairport.reservation.request.CarRequest;
import com.genesisairport.reservation.respository.CarImageRepository;
import com.genesisairport.reservation.respository.CarRepository;
import com.genesisairport.reservation.respository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

    private final String DEFAULT_IMAGE = "https://test.test/";
    private final CarImageRepository carImageRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public String getDefaultImage() {
        return DEFAULT_IMAGE;
    }

    public String getCarImage(String sellName) {
        Optional<CarImage> carImage = carImageRepository.findBySellName(sellName);
        if (carImage.isPresent())
            return carImage.get().getImageUrl();
        return DEFAULT_IMAGE;
    }

    public void saveCar(Long customerId, CarRequest.CarPost requestBody) {
        String sellName = requestBody.getSellName();
        String plateNumber = requestBody.getPlateNumber();
        carRepository.save(Car.builder()
                .customer(customerRepository.findCustomerById(customerId))
                .sellName(sellName)
                .plateNumber(plateNumber)
                .createDatetime(LocalDateTime.now())
                .updateDatetime(LocalDateTime.now())
                .build());
    }

    public void deleteCar(Long customerId, Long carId) {
        Optional<Car> car = carRepository.findById(carId);
        if (car.isEmpty() || !car.get().getCustomer().getId().equals(customerId)) {
            log.error("차량 삭제 실패");
            return;
        }
        carRepository.deleteById(carId);
    }
}
