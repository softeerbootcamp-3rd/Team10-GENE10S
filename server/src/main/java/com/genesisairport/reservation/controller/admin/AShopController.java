package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.service.admin.AShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2/admin/shop")
public class AShopController {
    private final AShopService aShopService;

    @PostMapping("/available")
    public ResponseEntity<ResponseDto> addAvailableDate(@RequestBody AdminRequest.ReservationTime requestBody) {

        return new ResponseEntity<>(ResponseDto.of(true, aShopService.addAvailableTime(requestBody)), HttpStatus.OK);
    }

    @DeleteMapping("/available")
    public ResponseEntity<ResponseDto> deleteAvailableDate(@RequestBody AdminRequest.ReservationTime requestBody) {

        return new ResponseEntity<>(ResponseDto.of(true, aShopService.deleteAvailableTime(requestBody)), HttpStatus.OK);
    }
}
