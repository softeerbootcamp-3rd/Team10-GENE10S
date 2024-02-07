package com.genesisairport.reservation.controller;

import com.genesisairport.reservation.common.DataResponseDto;
import com.genesisairport.reservation.entity.Customer;
import com.genesisairport.reservation.response.UserResponse;
import com.genesisairport.reservation.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final SessionService sessionService;

    @GetMapping("/profile")
    public ResponseEntity<DataResponseDto<UserResponse.Profile>> getResult(
            HttpServletRequest request
    ) {
        Optional<Customer> customer = sessionService.getLoggedInCustomer(request);
        UserResponse.Profile result = UserResponse.Profile.builder()
                .name("김희진")
                .carSellName("Genesis G80")
                .imageUrl("https://test.test/")
                .build();
        return new ResponseEntity<>(DataResponseDto.of(result), HttpStatus.OK);
    }
}
