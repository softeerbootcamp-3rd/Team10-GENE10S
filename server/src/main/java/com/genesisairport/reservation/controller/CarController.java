package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.request.CarRequest;
import com.genesisairport.reservation.service.CarService;
import com.genesisairport.reservation.common.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2/car")
public class CarController {

    private final CarService carService;
    private final SessionUtil sessionUtil;

    @PostMapping
    public ResponseEntity<ResponseDto> registerVehicle(HttpServletRequest request, @RequestBody CarRequest.CarPost requestBody) {
        log.info("차량 등록 API");
        Long userId = SessionUtil.getUserIdFromSession(request);

        if (userId == null) {
            throw new GeneralException(ResponseCode.FORBIDDEN, "유저를 불러오는 데에 실패했습니다.");
        }
        carService.saveCar(userId, requestBody);

        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<ResponseDto> deleteVehicle(HttpServletRequest request, @PathVariable Long carId) {
        log.info("차량 삭제 API");

        Long userId = SessionUtil.getUserIdFromSession(request);

        if (userId == null) {
            throw new GeneralException(ResponseCode.FORBIDDEN, "유저를 불러오는 데에 실패했습니다.");
        }
        carService.deleteCar(userId, carId);

        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }
}
