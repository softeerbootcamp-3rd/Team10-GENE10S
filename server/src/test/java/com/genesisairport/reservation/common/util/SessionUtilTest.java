package com.genesisairport.reservation.common.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;

public class SessionUtilTest {

    private RedisTemplate<String, Object> redisTemplate;
    private SessionUtil sessionUtil;

    @BeforeEach
    public void setUp() {
        redisTemplate = mock(RedisTemplate.class);
        sessionUtil = new SessionUtil(redisTemplate);
    }

    @Test
    public void testGetSessionKey() {
        String sessionId = "abcdef123456";
        String expectedSessionKey = "spring:session:sessions:abcdef123456";

        String resultSessionKey = sessionUtil.getSessionKey(sessionId);

        assertEquals(expectedSessionKey, resultSessionKey);
    }

    @Test
    public void testIsSessionExists() {
        String sessionKey = "spring:session:sessions:abcdef123456";

        when(redisTemplate.hasKey(sessionKey)).thenReturn(true);

        boolean result = sessionUtil.isSessionExists(sessionKey);

        assertTrue(result);
    }

    @Test
    public void testGetUserIdFromSession() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(123L);

        Long result = SessionUtil.getUserIdFromSession(request);

        assertEquals(123L, result);
    }

    @Test
    public void testGetAdminIdFromSession() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("adminId")).thenReturn(456L);

        Long result = SessionUtil.getAdminIdFromSession(request);

        assertEquals(456L, result);
    }
}
