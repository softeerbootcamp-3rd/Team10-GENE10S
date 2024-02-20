package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.GeneralException;
import com.genesisairport.reservation.common.ResponseCode;
import com.genesisairport.reservation.common.ResponseDto;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.service.SessionService;
import com.genesisairport.reservation.service.admin.AdminAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2/admin/account")
public class AdminAccountController {

    private final AdminAccountService adminAccountService;

    @PostMapping("/login")
    public ResponseEntity adminLogin(@RequestBody AdminRequest.Login loginDto, HttpServletRequest request) {

        Long adminId = adminAccountService.adminLogin(loginDto);

        HttpSession session = request.getSession(true);
        session.setAttribute("adminId", adminId);
        session.setMaxInactiveInterval(3600); // 1시간

        return new ResponseEntity(
                ResponseDto.of(true, ResponseCode.OK),
                HttpStatus.OK
        );
    }

    @PostMapping("/logout")
    public ResponseEntity adminLogout(HttpServletRequest request) {
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
