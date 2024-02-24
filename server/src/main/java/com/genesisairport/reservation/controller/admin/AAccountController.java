package com.genesisairport.reservation.controller.admin;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.common.model.DataResponseDto;
import com.genesisairport.reservation.common.model.PageInfo;
import com.genesisairport.reservation.common.model.PageResponseDto;
import com.genesisairport.reservation.common.model.ResponseDto;
import com.genesisairport.reservation.common.util.RedisUtil;
import com.genesisairport.reservation.entity.Admin;
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
    public ResponseEntity<DataResponseDto<AdminResponse.AdminInfo>> adminLogin(@RequestBody AdminRequest.Login loginDto, HttpServletRequest request) {
        Admin admin = adminAccountService.adminLogin(loginDto);

        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(24 * 60 * 60); // TODO 개발 끝나면 값 변경

        System.out.println("admin id: " + admin.getAdminName());
        return new ResponseEntity<>(
                DataResponseDto.of(AdminResponse.AdminInfo.builder()
                        .adminName(admin.getAdminName())
                        .build()),
                HttpStatus.OK
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> adminLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null)
            throw new GeneralException(ResponseCode.UNAUTHORIZED, "로그인 되어있지 않습니다.");

        session.invalidate();
        return new ResponseEntity<>(
                ResponseDto.of(true, ResponseCode.OK),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<List<AdminResponse.AccountDetail>>> searchAllAccounts(
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
    public ResponseEntity<DataResponseDto<Boolean>> isValidSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || redisUtil.isValid(session.getId()))
            return new ResponseEntity<>(
                    DataResponseDto.of(Boolean.FALSE)
                    , HttpStatus.OK
            );

        return new ResponseEntity<>(
                DataResponseDto.of(Boolean.TRUE),
                HttpStatus.OK
        );
    }

}
