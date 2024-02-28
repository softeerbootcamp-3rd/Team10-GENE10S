package com.genesisairport.reservation.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class QuerydslConfigTest {

    @InjectMocks
    private QuerydslConfig querydslConfig;

    @Test
    void jpaQueryFactory_shouldReturnNonNullInstance() {
        JPAQueryFactory jpaQueryFactory = querydslConfig.jpaQueryFactory();

        assertNotNull(jpaQueryFactory);
    }
}
