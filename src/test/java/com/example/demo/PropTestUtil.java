package com.example.demo;

import net.jqwik.api.Tuple;

import java.time.*;
import java.util.function.Function;

public class PropTestUtil {

    public static Function<LocalDateTime, ZonedDateTime> localDateToZonedDateTime() {
        return given -> ZonedDateTime.of(given.getYear(), given.getMonthValue(), given.getDayOfMonth(),
                given.getHour(), given.getMinute(), given.getSecond(), given.getNano(),
                ZoneId.systemDefault());
    }

    public static Function<LocalDateTime, OffsetDateTime> localDateToOffsetDateTime() {
        return given -> OffsetDateTime.of(given.getYear(), given.getMonthValue(), given.getDayOfMonth(),
                given.getHour(), given.getMinute(), given.getSecond(), given.getNano(),
                defaultOffset(given));
    }

    public static ZoneOffset defaultOffset(LocalDateTime localDateTime) {
        return ZoneId.systemDefault().getRules().getOffset(localDateTime);
    }

    public static <INPUT, ANSWER> Function<INPUT, Tuple.Tuple2<INPUT, ANSWER>> packWithAnswer(Function<? super INPUT, ? extends ANSWER> f) {
        return input -> Tuple.of(input, f.apply(input));
    }
}
