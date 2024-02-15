package com.genesisairport.reservation.service;

import com.genesisairport.reservation.entity.Customer;
import com.genesisairport.reservation.entity.Session;
import com.genesisairport.reservation.respository.CustomerRepository;
import com.genesisairport.reservation.respository.CustomSessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    public static Long getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Long) session.getAttribute("userId");
        }
        return null;
    }
}
