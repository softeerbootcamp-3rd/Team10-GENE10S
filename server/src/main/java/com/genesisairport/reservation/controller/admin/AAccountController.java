package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.model.DataResponseDto;
import com.genesisairport.reservation.common.model.PageInfo;
import com.genesisairport.reservation.common.model.PageResponseDto;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.common.util.RedisUtil;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import com.genesisairport.reservation.service.admin.AAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2/admin/account")
public class AAccountController {

    private final AAccountService adminAccountService;

    private final RedisUtil redisUtil;

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

    @GetMapping
    public ResponseEntity searchAllAccounts(
            Pageable pageable,
            @RequestParam String adminId,
            @RequestParam String adminName,
            @RequestParam String phoneNumber,
            @RequestParam String sortColumn,
            @RequestParam String sortDirection
            ) {

        AdminRequest.AccountDetail requestBody = AdminRequest.AccountDetail.builder()
                .adminId(adminId)
                .adminName(adminName)
                .phoneNumber(phoneNumber)
                .sortColumn(sortColumn)
                .sortDirection(sortDirection)
                .build();

        Page<AdminResponse.AccountDetail> accountDetailPage = adminAccountService.getAllAccounts(pageable, requestBody);

        // Page에서 필요한 정보 추출
        List<AdminResponse.AccountDetail> accountDetailList = accountDetailPage.getContent();
        PageInfo pageInfo = PageInfo.builder()
                .page(accountDetailPage.getNumber())
                .size(accountDetailPage.getSize())
                .totalElements(accountDetailPage.getTotalElements())
                .totalPages(accountDetailPage.getTotalPages())
                .build();

        return new ResponseEntity<>(
                PageResponseDto.of(accountDetailList, pageInfo),
                HttpStatus.OK
        );
    }

    @GetMapping("/session-validation")
    public ResponseEntity<ResponseDto> isValidSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Boolean valid = redisUtil.isValid(session.getId());

        // 유효하지 않은 세션인 경우
        if (!valid) {
            return new ResponseEntity<>(
                    ResponseDto.of(false, ResponseCode.UNAUTHORIZED),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                ResponseDto.of(true, ResponseCode.OK),
                HttpStatus.OK
        );
    }

}
