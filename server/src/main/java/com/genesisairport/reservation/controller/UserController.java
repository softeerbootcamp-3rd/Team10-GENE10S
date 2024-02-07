package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.DataResponseDto;
import com.genesisairport.reservation.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/user")
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<DataResponseDto<UserResponse.Profile>> getResult() {
        UserResponse.Profile result = UserResponse.Profile.builder()
                .name("김희진")
                .carSellName("Genesis G80")
                .imageUrl("https://test.test/")
                .build();
        return new ResponseEntity<>(DataResponseDto.of(result), HttpStatus.OK);
    }
}
