package com.genesisairport.reservation.service;

import com.genesisairport.reservation.entity.Customer;
import com.genesisairport.reservation.entity.Session;
import com.genesisairport.reservation.respository.CustomerRepository;
import com.genesisairport.reservation.respository.SessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    private final SessionRepository sessionRepository;
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
                Optional<Session> session = sessionRepository.findBySessionId(sid);
                if (session.isPresent()) {
                    Long customerId = session.get().getCustomerId();
                    return customerRepository.findById(customerId);
                }
            }
        }
        return Optional.empty();
    }
}
