package com.example.demo.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

class DateTimeUtilTest {

    @Test
    void localDateTime2zonedDateTime() {
        System.out.println(DateTimeUtil.localDateTime2zonedDateTime(LocalDateTime.now()));
    }

    @Test
    void localDateTime2offsetDateTime() {
        System.out.println(DateTimeUtil.localDateTime2offsetDateTime(LocalDateTime.now()));
    }

    @Test
    void offsetDateTime2LocalDateTime() {
        System.out.println(DateTimeUtil.offsetDateTime2LocalDateTime(OffsetDateTime.now()));
    }

    @Test
    void zonedDateTime2LocalDateTime() {
        System.out.println(DateTimeUtil.zonedDateTime2LocalDateTime(ZonedDateTime.now()));
    }

    @Test
    void date2LocalDate() {
        System.out.println(DateTimeUtil.date2LocalDate(new Date()));
    }

    @Test
    void localDate2Date() {
        System.out.println(DateTimeUtil.localDate2Date(LocalDate.now()));
    }
}