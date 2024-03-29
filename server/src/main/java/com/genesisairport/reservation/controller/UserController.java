package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.model.DataResponseDto;
import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.request.UserRequest;
import com.genesisairport.reservation.response.UserResponse;
import com.genesisairport.reservation.common.util.SessionUtil;
import com.genesisairport.reservation.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<DataResponseDto<UserResponse.UserInfo>> getUserInfo(
            final HttpServletRequest request
    ) {
        Long userId = SessionUtil.getUserIdFromSession(request);

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
        Long userId = SessionUtil.getUserIdFromSession(request);
        userService.patchUserInfo(userId, userInfo);
        return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK), HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<DataResponseDto<UserResponse.Profile>> getResult(
            final HttpServletRequest request
    ) {
        Long userId = SessionUtil.getUserIdFromSession(request);
        return new ResponseEntity<>(
                DataResponseDto.of(userService.getUserProfile(userId)),
                HttpStatus.OK
        );
    }
}
