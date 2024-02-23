package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.common.util.SessionUtil;
import com.genesisairport.reservation.request.UserRequest;
import com.genesisairport.reservation.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class AuthController {
    
    private final AuthService authService;
    private final SessionUtil sessionUtil;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequest.Login login, HttpServletRequest request) {
        // OAuth 토큰 발급
        String accessToken = authService.tokenRequest(login);

        if (accessToken.isEmpty() || accessToken.isBlank()) {
            return new ResponseEntity<>(
                    ResponseDto.of(false, ResponseCode.BAD_REQUEST),
                    HttpStatus.OK
            );
        }

        // 사용자 정보 조회 및 저장
        long userId = authService.userProfileRequest(accessToken);
        // 세션 생성
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", userId);
        session.setMaxInactiveInterval(24 * 60 * 60); // TODO 개발 다 끝나면 시간 변경

        return new ResponseEntity(
                ResponseDto.of(true, ResponseCode.OK),
                HttpStatus.OK
        );
    }

    @GetMapping("/login")
    public ResponseEntity isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return new ResponseEntity<>(
                    ResponseDto.of(false, ResponseCode.UNAUTHORIZED),
                    HttpStatus.OK
            );
        }

        String sessionKey = sessionUtil.getSessionKey(session.getId());
        if (!sessionUtil.isSessionExists(sessionKey)) {
            return new ResponseEntity<>(
                    ResponseDto.of(false, ResponseCode.UNAUTHORIZED),
                    HttpStatus.OK
            );
        }

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
