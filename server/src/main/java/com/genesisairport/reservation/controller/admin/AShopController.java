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
    public ResponseEntity<ResponseDto> getAvailableDate(@RequestParam(value = "shopName") String shopName,
                                                        @RequestParam(value = "businessDayFrom") String businessDayFrom,
                                                        @RequestParam(value = "businessDayTo") String businessDayTo) {
        log.debug("예약 가능 시간 조회");
        if (shopName.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "지점명을 선택해주세요.");
        }
        if (businessDayFrom.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "검색 시작 날짜를 입력해주세요.");
        }
        if (businessDayTo.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "검색 끝 날짜를 입력해주세요.");
        }

        return new ResponseEntity<>(DataResponseDto.of(aShopService.getAvailableTime(shopName, businessDayFrom, businessDayTo)), HttpStatus.OK);
    }

    @PostMapping("/available")
    public ResponseEntity<ResponseDto> addAvailableDate(@RequestBody AdminRequest.ReservationTime requestBody) {
        log.debug("예약 가능시간 추가");
        if (requestBody.getShopName() == null) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "지점명을 선택해주세요.");
        }
        if (requestBody.getBusinessDay() == null) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "시간 정보가 없습니다.");
        }

        return new ResponseEntity<>(ResponseDto.of(true, aShopService.addAvailableTime(requestBody)), HttpStatus.OK);
    }

    @DeleteMapping("/available")
    public ResponseEntity<ResponseDto> deleteAvailableDate(@RequestParam(value = "shopName") String shopName,
                                                           @RequestParam(value = "businessDay") String businessDay) {
        log.debug("예약 가능 시간 삭제");
        if (shopName.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "지점명을 선택해주세요.");
        }
        if (businessDay.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "시간 정보가 없습니다.");
        }

        return new ResponseEntity<>(ResponseDto.of(true, aShopService.deleteAvailableTime(shopName, businessDay)), HttpStatus.OK);
    }
    @DeleteMapping("/cancel")
    public ResponseEntity<ResponseDto> deleteAvailableDateWithReservation(@RequestParam(value = "shopName") String shopName,
                                                                          @RequestParam(value = "businessDay") String businessDay,
                                                                          @RequestParam(value = "message") String message) {
        log.debug("예약되어있는 시간 삭제");
        if (shopName.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "지점명을 선택해주세요.");
        }
        if (businessDay.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "시간 정보가 없습니다.");
        }
        if (message.isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "메시지가 없습니다.");
        }

        return new ResponseEntity<>(ResponseDto.of(true, aShopService.deleteAvailableTimeWithReservation(shopName, businessDay, message)), HttpStatus.OK);
    }
}
