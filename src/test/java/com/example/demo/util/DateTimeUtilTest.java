package com.example.demo.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeUtilTest {

    static OffsetDateTime offsetDateTime;
    static ZonedDateTime zonedDateTime;
    static LocalDateTime localDateTime;

    @BeforeAll
    static void setupStatic() {
        offsetDateTime = OffsetDateTime.of(2021, 9, 21, 9, 0, 0, 0, ZoneOffset.ofHours(9));
        localDateTime = LocalDateTime.of(2021, 9, 21, 9, 0, 0, 0);
        zonedDateTime = offsetDateTime.toZonedDateTime();
    }

    @Test
    void localDateTime2zonedDateTime() {
        assertThat(DateTimeUtil.localDateTime2zonedDateTime(localDateTime))
                .isEqualTo(zonedDateTime);
    }

    @Test
    void localDateTime2offsetDateTime() {
        assertThat(DateTimeUtil.localDateTime2offsetDateTime(localDateTime))
                .isEqualTo(offsetDateTime);
    }

    @Test
    void offsetDateTime2LocalDateTime() {
        assertThat(DateTimeUtil.offsetDateTime2LocalDateTime(offsetDateTime))
                .isEqualTo(localDateTime);
    }

    @Test
    void zonedDateTime2LocalDateTime() {
        assertThat(DateTimeUtil.zonedDateTime2LocalDateTime(zonedDateTime))
                .isEqualTo(localDateTime);
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