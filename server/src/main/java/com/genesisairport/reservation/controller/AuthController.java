package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.ResponseCode;
import com.genesisairport.reservation.common.ResponseDto;
import com.genesisairport.reservation.request.UserRequest;
import com.genesisairport.reservation.service.AuthService;
import com.genesisairport.reservation.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/v1")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequest.Login login, HttpServletRequest request) {
        // OAuth 토큰 발급
        String accessToken = authService.tokenRequest(login);
        // 사용자 정보 조회 및 저장
        long userId = authService.userProfileRequest(accessToken);
        // 세션 생성
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", userId);
        session.setMaxInactiveInterval(3600); // 1시간

        return new ResponseEntity(
                ResponseDto.of(true, ResponseCode.OK),
                HttpStatus.OK
        );
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return new ResponseEntity(
                    ResponseDto.of(true, ResponseCode.OK),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity(
                ResponseDto.of(true, ResponseCode.UNAUTHORIZED),
                HttpStatus.OK
        );
    }

}
