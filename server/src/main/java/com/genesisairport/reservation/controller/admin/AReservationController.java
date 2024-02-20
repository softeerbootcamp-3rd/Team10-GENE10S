package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.DataResponseDto;
import com.genesisairport.reservation.common.GeneralException;
import com.genesisairport.reservation.common.ResponseCode;
import com.genesisairport.reservation.common.ResponseDto;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.service.SessionService;
import com.genesisairport.reservation.service.admin.AReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2/admin")
public class AReservationController {
    private final AReservationService aReservationService;

    @GetMapping("/reservation/all")
    public ResponseEntity<ResponseDto> searchAllReservations(HttpServletRequest request, @RequestBody AdminRequest.ReservationDetail requestBody) {
        Long userId = SessionService.getUserIdFromSession(request);

        if (!Objects.isNull(userId)) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "로그인이 필요합니다.");
        }
        return new ResponseEntity(
                DataResponseDto.of(aReservationService.getAllReservations(requestBody)),
                HttpStatus.OK
        );
    }
}
