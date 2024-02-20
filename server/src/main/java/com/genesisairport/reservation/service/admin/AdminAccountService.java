package com.genesisairport.reservation.service.admin;

import com.genesisairport.reservation.entity.Admin;
import com.genesisairport.reservation.request.AdminRequest;
import com.genesisairport.reservation.respository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminAccountService {

    private final AdminRepository adminRepository;

    public Long adminLogin(AdminRequest.Login loginDto) {
        String adminId = loginDto.getAdminId();
        String adminPwd = loginDto.getAdminPwd();

        Admin admin = adminRepository.findByAdminId(adminId).orElse(null);

        // 아이디 존재 x
        if (admin == null)
            return null;

        // 비밀번호 일치 x
        String encryptedPwd = encryptPassword(adminPwd);
        if (!admin.getAdminPassword().equals(encryptedPwd))
            return null;

        return admin.getId();
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
            e.printStackTrace();
            return null;
        }
    }



}
