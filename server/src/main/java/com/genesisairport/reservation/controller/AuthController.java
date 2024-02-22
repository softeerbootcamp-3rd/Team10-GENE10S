package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.DataResponseDto;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.request.UserRequest;
import com.genesisairport.reservation.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody UserRequest.Login login, HttpServletRequest request) {
        // OAuth 토큰 발급
        String accessToken = authService.tokenRequest(login);

        if (Strings.isEmpty(accessToken)) {
            throw new GeneralException(ResponseCode.UNAUTHORIZED, "토큰 발급에 실패했습니다.");
        }

        // 사용자 정보 조회 및 저장
        long userId = authService.userProfileRequest(accessToken);

        // 세션 생성
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", userId);
        session.setMaxInactiveInterval(3600); // 1시간

        return new ResponseEntity<>(
                ResponseDto.of(true, ResponseCode.OK),
                HttpStatus.OK
        );
    }

    @GetMapping("/login/check")
    public ResponseEntity<DataResponseDto<Boolean>> isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") != null) {
            return new ResponseEntity<>(
                    DataResponseDto.of(false),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                DataResponseDto.of(true),
                HttpStatus.OK
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new GeneralException(ResponseCode.UNAUTHORIZED, "로그인 정보를 불러오는 데에 실패했습니다.");
        }
        session.invalidate();
        return new ResponseEntity<>(
                ResponseDto.of(true, ResponseCode.OK),
                HttpStatus.OK
        );
    }

}
