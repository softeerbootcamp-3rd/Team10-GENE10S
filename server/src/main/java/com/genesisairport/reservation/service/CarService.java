package com.genesisairport.reservation.service;

import com.genesisairport.reservation.entity.Car;
import com.genesisairport.reservation.entity.CarImage;
import com.genesisairport.reservation.repository.CarImageRepository;
import com.genesisairport.reservation.repository.CarRepository;
import com.genesisairport.reservation.repository.CustomerRepository;
import com.genesisairport.reservation.request.CarRequest;
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

        //TODO: 차량 레코드가 가진 고객 정보와 로그인한 고객 정보가 일치하지 않을 때 예외처리
        if (car.isEmpty() || !car.get().getCustomer().getId().equals(customerId)) {
            log.error("차량 삭제 실패");
            return;
        }
        carRepository.deleteById(carId);
    }
}
