package com.genesisairport.reservation.service.admin;

import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import com.genesisairport.reservation.entity.Admin;
import com.genesisairport.reservation.repository.AdminRepository;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.response.AdminResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AAccountService {

    private final AdminRepository adminRepository;

    public Admin adminLogin(AdminRequest.Login loginDto) {
        String adminId = loginDto.getAdminId();
        String adminPwd = loginDto.getAdminPwd();

        Optional<Admin> admin = adminRepository.findByAdminId(adminId);

        // 아이디 존재 x
        if (admin.isEmpty())
            throw new GeneralException(ResponseCode.NOT_FOUND, "존재하지 않는 아이디입니다.");

        // 비밀번호 일치 x
        String encryptedPwd = encryptPassword(adminPwd);
        if (!admin.get().getAdminPassword().equals(encryptedPwd))
            throw new GeneralException(ResponseCode.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

        return admin.get();
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] hashedBytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                sb.append(Integer.toString((hashedByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new GeneralException(ResponseCode.INTERNAL_SERVER_ERROR, "비밀번호 암호화에 실패했습니다.");
        }
    }

    public Page<AdminResponse.AccountDetail> getAllAccounts(Pageable pageable, AdminRequest.AccountDetail requestBody) {
        return adminRepository.findAccounts(pageable, requestBody);
    }

}
