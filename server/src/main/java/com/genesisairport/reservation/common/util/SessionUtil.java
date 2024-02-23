package com.genesisairport.reservation.common.util;


import com.genesisairport.reservation.common.enums.ResponseCode;
import com.genesisairport.reservation.common.exception.GeneralException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SessionUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String SESSION_PREFIX = "spring:session:sessions:";

    public String getSessionKey(String sessionId) {
        return SESSION_PREFIX + sessionId;
    }

    public Boolean isSessionExists(String sessionKey) {
        return redisTemplate.hasKey(sessionKey);
    }

    public static Long getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        return Optional.ofNullable(session)
                    .map(s -> (Long) s.getAttribute("userId"))
                    .orElse(null);
    }

    public static Long getAdminIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return Optional.ofNullable(session)
                    .map(s -> (Long) s.getAttribute("adminId"))
                    .orElse(null);
    }

}
