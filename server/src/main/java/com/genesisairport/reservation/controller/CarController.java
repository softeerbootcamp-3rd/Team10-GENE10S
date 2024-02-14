package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.ResponseCode;
import com.genesisairport.reservation.common.ResponseDto;
import com.genesisairport.reservation.entity.Customer;
import com.genesisairport.reservation.request.CarRequest;
import com.genesisairport.reservation.service.CarService;
import com.genesisairport.reservation.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/v2/car")
public class CarController {

    private final CarService carService;
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<ResponseDto> registerVehicle(HttpServletRequest request, @RequestBody CarRequest.CarPost requestBody) {
        log.info("차량 등록 API");
        Optional<Customer> customer = sessionService.getLoggedInCustomer(request);

        //TODO: 로그인 유저 없을때 예외처리
        if (customer.isEmpty()) {
            return new ResponseEntity<>(ResponseDto.of(false, ResponseCode.INTERNAL_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        carService.saveCar(customer.get().getId(), requestBody);

        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<ResponseDto> deleteVehicle(HttpServletRequest request, @PathVariable Long carId) {
        log.info("차량 삭제 API");

        Optional<Customer> customer = sessionService.getLoggedInCustomer(request);

        //TODO: 로그인 유저 없을때 예외처리
        if (customer.isEmpty()) {
            return new ResponseEntity<>(ResponseDto.of(false, ResponseCode.INTERNAL_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        carService.deleteCar(customer.get().getId(), carId);

        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }
}
