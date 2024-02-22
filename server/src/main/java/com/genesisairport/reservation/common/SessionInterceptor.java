package com.genesisairport.reservation.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

    private static final String SESSION_PREFIX = "spring:session:sessions:";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // CORS: preflight 요청 시에는 세션 검증 X
        if (StringUtils.equals(request.getMethod(), "OPTIONS")) {
            return true;
        }

        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String sessionKey = getSessionKey(session.getId());

        if (!isSessionExists(sessionKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }

    private String getSessionKey(String sessionId) {
        return SESSION_PREFIX + sessionId;
    }

    private Boolean isSessionExists(String sessionKey) {
        return redisTemplate.hasKey(sessionKey);
    }
}
