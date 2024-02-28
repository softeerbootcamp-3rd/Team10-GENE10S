package com.genesisairport.reservation.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class CommonDateFormatTest {

    @Test
    public void testDateWithLocalDate() {
        LocalDate localDate = LocalDate.of( 2024, 2, 28);
        String formattedDate = CommonDateFormat.date(localDate);
        assertEquals("2024-02-28", formattedDate);
    }

    @Test
    public void testDateWithString() {
        String dateString = "2024-02-28 10:15:30";
        Date date = CommonDateFormat.date(dateString);
        assertEquals(Date.valueOf("2024-02-28"), date);
    }

    @Test
    public void testDateWithLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of( 2024, 2, 28, 10, 15, 30);
        Date date = CommonDateFormat.date(localDateTime);
        assertEquals(Date.valueOf("2024-02-28"), date);
    }

    @Test
    public void testLocalDateWithString() {
        String dateString = "2024-02-28";
        LocalDate localDate = CommonDateFormat.localDate(dateString);
        assertEquals(LocalDate.of( 2024, 2, 28), localDate);
    }

    @Test
    public void testTimeWithLocalTime() {
        LocalTime localTime = LocalTime.of(10, 15);
        String formattedTime = CommonDateFormat.time(localTime);
        assertEquals("10:15", formattedTime);
    }

    @Test
    public void testTimeWithString() {
        String timeString = "2024-02-28 10:15:30";
        Time time = CommonDateFormat.time(timeString);
        assertEquals(Time.valueOf("10:15:30"), time);
    }

    @Test
    public void testLocalDateTimeWithString() {
        String dateTimeString = "2024-02-28 10:15:30";
        LocalDateTime localDateTime = CommonDateFormat.localDateTime(dateTimeString);
        assertEquals(LocalDateTime.of( 2024, 2, 28, 10, 15, 30), localDateTime);
    }

    @Test
    public void testLocalDateTimeWithLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of( 2024, 2, 28, 10, 15, 30);
        String formattedDateTime = CommonDateFormat.localDateTime(localDateTime);
        assertEquals("2024-02-28 10:15:30", formattedDateTime);
    }
}
