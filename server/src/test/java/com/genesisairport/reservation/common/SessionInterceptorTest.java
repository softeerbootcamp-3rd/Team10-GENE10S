package com.genesisairport.reservation.common;

import com.genesisairport.reservation.common.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private SessionUtil sessionUtil;

    @InjectMocks
    private SessionInterceptor interceptor;

    @Test
    void preHandle_withValidSession_shouldReturnTrue() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getId()).thenReturn("validSessionId");
        when(sessionUtil.getSessionKey("validSessionId")).thenReturn("validSessionKey");
        when(sessionUtil.isSessionExists("validSessionKey")).thenReturn(true);

        boolean result = interceptor.preHandle(request, response, null);

        assertTrue(result);
        verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void preHandle_withNullSession_shouldReturnFalseAndSetUnauthorizedStatus() throws Exception {
        when(request.getSession(false)).thenReturn(null);

        boolean result = interceptor.preHandle(request, response, null);

        assertFalse(result);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void preHandle_withInvalidSession_shouldReturnFalseAndSetUnauthorizedStatus() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getId()).thenReturn("invalidSessionId");
        when(sessionUtil.getSessionKey("invalidSessionId")).thenReturn("invalidSessionKey");
        when(sessionUtil.isSessionExists("invalidSessionKey")).thenReturn(false);

        boolean result = interceptor.preHandle(request, response, null);

        assertFalse(result);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
