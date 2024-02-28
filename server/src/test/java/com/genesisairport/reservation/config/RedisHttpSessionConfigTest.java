package com.genesisairport.reservation.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.SessionRepository;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisHttpSessionConfigTest {

    @Mock
    private RedisConnectionFactory redisConnectionFactory;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private RedisHttpSessionConfig redisHttpSessionConfig;

    @Test
    void redisIndexedSessionRepository_shouldReturnNonNullInstance() {
        RedisTemplate<String, Object> redisTemplate = mock(RedisTemplate.class);

        SessionRepository sessionRepository = redisHttpSessionConfig.redisIndexedSessionRepository(redisTemplate);

        assertNotNull(sessionRepository);
    }

    @Test
    void redisTemplate_shouldReturnNonNullInstance() {
        RedisTemplate<String, Object> redisTemplate = redisHttpSessionConfig.redisTemplate(redisConnectionFactory);

        assertNotNull(redisTemplate);
    }
}
