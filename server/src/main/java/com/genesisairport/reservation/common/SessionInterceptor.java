package com.genesisairport.reservation.common;

import com.genesisairport.reservation.common.util.SessionUtil;
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
    
    private final SessionUtil sessionUtil;

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

        String sessionKey = sessionUtil.getSessionKey(session.getId());

        if (!sessionUtil.isSessionExists(sessionKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}
