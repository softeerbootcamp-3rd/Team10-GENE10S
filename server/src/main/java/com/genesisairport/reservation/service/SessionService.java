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

    private final CustomSessionRepository customSessionRepository;
    private final CustomerRepository customerRepository;

    public Optional<Customer> getLoggedInCustomer(HttpServletRequest request) {
        if (request == null)
            return Optional.empty();

        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return Optional.empty();

        for (Cookie cookie : cookies) {
            if ("sid".equals(cookie.getName())) {
                String sid = cookie.getValue();
                Optional<Session> session = customSessionRepository.findBySessionId(sid);
                if (session.isPresent()) {
                    Long customerId = session.get().getCustomerId();
                    return customerRepository.findById(customerId);
                }
            }
        }
        return Optional.empty();
    }

    public static Long getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Long) session.getAttribute("userId");
        }
        return null;
    }
}
