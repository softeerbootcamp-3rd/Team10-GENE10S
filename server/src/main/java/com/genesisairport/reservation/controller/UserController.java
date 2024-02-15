package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.DataResponseDto;
import com.genesisairport.reservation.common.ResponseCode;
import com.genesisairport.reservation.common.ResponseDto;
import com.genesisairport.reservation.request.UserRequest;
import com.genesisairport.reservation.response.UserResponse;
import com.genesisairport.reservation.service.SessionService;
import com.genesisairport.reservation.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<DataResponseDto<UserResponse.UserInfo>> getUserInfo(
            final HttpServletRequest request
    ) {
        Long userId = SessionService.getUserIdFromSession(request);

        return new ResponseEntity<>(
                DataResponseDto.of(userService.getUserInfo(userId)),
                HttpStatus.OK
        );
    }

    @PatchMapping("/info")
    public ResponseEntity<ResponseDto> patchUserInfo(
            final HttpServletRequest request,
            @RequestBody UserRequest.UserInfo userInfo
    ) {
        Long userId = SessionService.getUserIdFromSession(request);
        userService.patchUserInfo(userId, userInfo);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<DataResponseDto<UserResponse.Profile>> getResult(
            final HttpServletRequest request
    ) {
        Long userId = SessionService.getUserIdFromSession(request);
        return new ResponseEntity<>(
                DataResponseDto.of(userService.getUserProfile(userId)),
                HttpStatus.OK
        );
    }
}
