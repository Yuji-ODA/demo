package com.example.demo.util;

import com.example.demo.PropTestUtil;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.time.api.DateTimes;
import net.jqwik.time.api.Dates;
import net.jqwik.time.api.Times;

import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

import static com.example.demo.PropTestUtil.packWithAnswer;
import static com.example.demo.util.DateTimeUtil.*;
import static net.jqwik.api.Tuple.Tuple2;
import static org.assertj.core.api.Assertions.assertThat;

class DateTimeUtilProp {

    @Property
    void localDateTime_is_correctly_converted_to_ZonedDateTime(@ForAll("localAndZoned") Tuple2<LocalDateTime, ZonedDateTime> given) {
        ZonedDateTime actual = toZonedDateTime(given.get1());
        ZonedDateTime expected = given.get2();

        assertThat(actual).isEqualTo(expected);
    }

    @Property
    void zonedDateTime_is_correctly_converted_to_LocalDateTime(@ForAll("zonedAndLocal") Tuple2<ZonedDateTime, LocalDateTime> given) {
        LocalDateTime actual = toLocalDateTime(given.get1());
        LocalDateTime expected = given.get2();

        assertThat(actual).isEqualTo(expected);
    }

    @Property
    void localDateTime_is_correctly_converted_to_OffsetDateTime(@ForAll("localAndOffset") Tuple2<LocalDateTime, OffsetDateTime> given) {
        OffsetDateTime actual = toOffsetDateTime(given.get1());
        OffsetDateTime expected = given.get2();

        assertThat(actual).isEqualTo(expected);
    }

    @Property
    void offsetDateTime_is_correctly_converted_to_LocalDateTime(@ForAll("offsetAndLocal") Tuple2<OffsetDateTime, LocalDateTime> given) {
        LocalDateTime actual = toLocalDateTime(given.get1());
        LocalDateTime expected = given.get2();

        assertThat(actual).isEqualTo(expected);
    }

    @Property
    void date_is_correctly_converted_to_LocalDate(@ForAll("dates") Date date) {
        LocalDateTime converted = toLocalDate(date).atStartOfDay();
        Instant actual = localDateTimeToInstant(converted);
        Instant expected = date.toInstant();

        assertThat(actual).isEqualTo(expected);
    }

    @Property
    void sqlDate_is_correctly_converted_to_LocalDate(@ForAll("sqlDates") java.sql.Date date) {
        LocalDateTime converted = toLocalDate(date).atStartOfDay();
        Instant actual = localDateTimeToInstant(converted);
        Instant expected = new Date(date.getTime()).toInstant();

        assertThat(actual).isEqualTo(expected);
    }

    @Property
    void localDate_is_correctly_converted_to_Date(@ForAll("localDates") LocalDate localDate) {
        Date converted = toDate(localDate);
        Instant actual = converted.toInstant();
        Instant expected = localDateTimeToInstant(localDate.atStartOfDay());

        assertThat(actual).isEqualTo(expected);
    }

    @Property
    void localDate_is_correctly_converted_to_SqlDate(@ForAll("localDates") LocalDate localDate) {
        java.sql.Date converted = toSqlDate(localDate);
        Instant actual = new Date(converted.getTime()).toInstant();
        Instant expected = localDateTimeToInstant(localDate.atStartOfDay());

        assertThat(actual).isEqualTo(expected);
    }

    @Provide
    Arbitrary<LocalDateTime> localDateTimes() {
        return DateTimes.dateTimes();
    }

    @Provide
    Arbitrary<OffsetDateTime> offsetDateTimes() {
        return DateTimes.offsetDateTimes();
    }

    @Provide
    Arbitrary<ZonedDateTime> zonedDatetimes() {
        return offsetDateTimes().map(OffsetDateTime::toZonedDateTime);
    }

    @Provide
    Arbitrary<LocalDate> localDates() {
        return Dates.dates();
    }

    @Provide
    Arbitrary<Date> dates() {
        return Dates.datesAsDate();
    }

    @Provide
    Arbitrary<java.sql.Date> sqlDates() {
        return Dates.datesAsCalendar()
                .map(Calendar::getTime)
                .map(Date::getTime)
                .map(java.sql.Date::new);
    }

    @Provide
    Arbitrary<Tuple2<LocalDateTime, ZonedDateTime>> localAndZoned() {
        return Times.zoneIds()
                .flatMap(zoneId -> localDateTimes().map(packWithAnswer(localDateToZonedDateTime(zoneId))));
    }

    @Provide
    Arbitrary<Tuple2<LocalDateTime, OffsetDateTime>> localAndOffset() {
        return Times.zoneIds()
                .flatMap(zoneId -> localDateTimes().map(packWithAnswer(localDateToOffsetDateTime(zoneId))));
    }

    @Provide
    Arbitrary<Tuple2<ZonedDateTime, LocalDateTime>> zonedAndLocal() {
        return localAndZoned().map(PropTestUtil::reverse);
    }

    @Provide
    Arbitrary<Tuple2<OffsetDateTime, LocalDateTime>> offsetAndLocal() {
        return localAndOffset().map(PropTestUtil::reverse);
    }

    static Function<LocalDateTime, ZonedDateTime> localDateToZonedDateTime(ZoneId zoneId) {
        return localDateTime -> ZonedDateTime.of(localDateTime, ZoneId.systemDefault())
                .withZoneSameInstant(zoneId);
    }

    static Function<LocalDateTime, OffsetDateTime> localDateToOffsetDateTime(ZoneId zoneId) {
        return localDateToZonedDateTime(zoneId).andThen(ZonedDateTime::toOffsetDateTime);
    }

    static Instant localDateTimeToInstant(LocalDateTime from) {
        return from.toInstant(ZoneId.systemDefault().getRules().getOffset(from));
    }
}
