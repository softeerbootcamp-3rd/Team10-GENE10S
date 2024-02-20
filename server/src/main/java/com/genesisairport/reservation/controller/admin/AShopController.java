package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.DataResponseDto;
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

    @GetMapping("/available")
    public ResponseEntity<ResponseDto> getAvailableDate(@RequestBody AdminRequest.ReservationTimeRange requestBody) {
        if (requestBody.getShopName() == null) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "지점명을 선택해주세요.");
        }
        if (requestBody.getBusinessDayFrom() == null) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "검색 시작 날짜ㅁ를 입력해주세요.");
        }
        if (requestBody.getBusinessDayTo() == null) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "검색 끝 날짜를 입력해주세요.");
        }

        return new ResponseEntity<>(DataResponseDto.of(aShopService.getAvailableTime(requestBody)), HttpStatus.OK);
    }

    @PostMapping("/available")
    public ResponseEntity<ResponseDto> addAvailableDate(@RequestBody AdminRequest.ReservationTime requestBody) {
        if (requestBody.getShopName() == null) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "지점명을 선택해주세요.");
        }
        if (requestBody.getBusinessDay() == null) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "시간 정보가 없습니다.");
        }

        return new ResponseEntity<>(ResponseDto.of(true, aShopService.addAvailableTime(requestBody)), HttpStatus.OK);
    }

    @DeleteMapping("/available")
    public ResponseEntity<ResponseDto> deleteAvailableDate(@RequestBody AdminRequest.ReservationTime requestBody) {
        if (requestBody.getShopName() == null) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "지점명을 선택해주세요.");
        }
        if (requestBody.getBusinessDay() == null) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "시간 정보가 없습니다.");
        }

        return new ResponseEntity<>(ResponseDto.of(true, aShopService.deleteAvailableTime(requestBody)), HttpStatus.OK);
    }
}
