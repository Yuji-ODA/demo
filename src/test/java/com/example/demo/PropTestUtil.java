package com.example.demo;

import net.jqwik.api.Tuple;

import java.time.*;
import java.util.function.Function;

public class PropTestUtil {

    public static Function<LocalDateTime, ZonedDateTime> localDateToZonedDateTime(ZoneId zoneId) {
        return given -> ZonedDateTime.of(given.getYear(), given.getMonthValue(), given.getDayOfMonth(),
                given.getHour(), given.getMinute(), given.getSecond(), given.getNano(),
                ZoneId.systemDefault()).withZoneSameInstant(zoneId);
    }

    public static Function<LocalDateTime, OffsetDateTime> localDateToOffsetDateTime(ZoneOffset zoneOffset) {
        return localDateToZonedDateTime(zoneOffset).andThen(ZonedDateTime::toOffsetDateTime);
    }

    public static <INPUT, ANSWER> Function<INPUT, Tuple.Tuple2<INPUT, ANSWER>> packWithAnswer(Function<? super INPUT, ? extends ANSWER> f) {
        return input -> Tuple.of(input, f.apply(input));
    }
}
