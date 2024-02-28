package com.genesisairport.reservation.common.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageInfoTest {
    @Test
    public void testConstructorAndGetters() {
        long page = 1;
        long size = 10;
        long totalElements = 100;
        long totalPages = 10;

        PageInfo pageInfo = new PageInfo(page, size, totalElements, totalPages);

        assertEquals(page, pageInfo.getPage());
        assertEquals(size, pageInfo.getSize());
        assertEquals(totalElements, pageInfo.getTotalElements());
        assertEquals(totalPages, pageInfo.getTotalPages());
    }

    @Test
    public void testBuilder() {
        long page = 1;
        long size = 10;
        long totalElements = 100;
        long totalPages = 10;

        PageInfo pageInfo = PageInfo.builder()
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();

        assertEquals(page, pageInfo.getPage());
        assertEquals(size, pageInfo.getSize());
        assertEquals(totalElements, pageInfo.getTotalElements());
        assertEquals(totalPages, pageInfo.getTotalPages());
    }

    @Test
    public void testNoArgsConstructor() {
        PageInfo pageInfo = new PageInfo();

        assertEquals(0, pageInfo.getPage());
        assertEquals(0, pageInfo.getSize());
        assertEquals(0, pageInfo.getTotalElements());
        assertEquals(0, pageInfo.getTotalPages());
    }
}
