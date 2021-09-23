package com.example.demo.util;

import com.example.demo.PropTestUtil;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Function;

import static com.example.demo.PropTestUtil.packWithAnswer;
import static com.example.demo.util.DateTimeUtil.*;
import static net.jqwik.api.Tuple.Tuple2;
import static net.jqwik.time.api.DateTimes.dateTimes;
import static net.jqwik.time.api.Times.zoneIds;
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


    @Provide
    Arbitrary<Tuple2<LocalDateTime, ZonedDateTime>> localAndZoned() {
        return zoneIds()
                .flatMap(zoneId -> dateTimes().map(packWithAnswer(localDateToZonedDateTime(zoneId))));
    }

    @Provide
    Arbitrary<Tuple2<LocalDateTime, OffsetDateTime>> localAndOffset() {
        return zoneIds()
                .flatMap(zoneId -> dateTimes().map(packWithAnswer(localDateToOffsetDateTime(zoneId))));
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
}
