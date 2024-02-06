package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.Request.LoginRequest;
import com.genesisairport.reservation.Response.LoginResponse;
import com.genesisairport.reservation.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/v1")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/oauth/token")
    public ResponseEntity<Object> login(@RequestBody LoginRequest.Login login) {
        return ResponseEntity.ok(LoginResponse.Login.builder().sid(
                authService.tokenRequest(login))
                .build());
    }
}
