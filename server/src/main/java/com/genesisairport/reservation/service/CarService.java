package com.genesisairport.reservation.service;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.entity.Car;
import com.genesisairport.reservation.entity.CarImage;
import com.genesisairport.reservation.entity.Customer;
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
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new GeneralException(ResponseCode.NOT_FOUND, "고객 정보를 찾을 수 없습니다.");
        }
        String sellName = requestBody.getSellName();
        String plateNumber = requestBody.getPlateNumber();
        carRepository.save(Car.builder()
                .customer(customer.get())
                .sellName(sellName)
                .plateNumber(plateNumber)
                .createDatetime(LocalDateTime.now())
                .updateDatetime(LocalDateTime.now())
                .build());
    }

    public void deleteCar(Long customerId, Long carId) {
        Optional<Car> car = carRepository.findById(carId);

        if (car.isEmpty() || !car.get().getCustomer().getId().equals(customerId)) {
            throw new GeneralException(ResponseCode.FORBIDDEN, "차량 소유주가 아닙니다.");
        }
        carRepository.deleteById(carId);
    }
}
