package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.DataResponseDto;
import com.genesisairport.reservation.common.ResponseCode;
import com.genesisairport.reservation.common.ResponseDto;
import com.genesisairport.reservation.request.UserRequest;
import com.genesisairport.reservation.response.UserResponse;
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
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                DataResponseDto.of(userService.getUserInfo(request)),
                HttpStatus.OK
        );
    }

    @PatchMapping("/info")
    public ResponseEntity<ResponseDto> patchUserInfo(
            HttpServletRequest request,
            @RequestBody UserRequest.UserInfo userInfo
    ) {
        userService.patchUserInfo(request, userInfo);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<DataResponseDto<UserResponse.Profile>> getResult(
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                DataResponseDto.of(userService.getUserProfile(request)),
                HttpStatus.OK
        );
    }
}
