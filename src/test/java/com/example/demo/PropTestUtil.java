package com.example.demo;

import net.jqwik.api.Tuple;

import java.util.function.Function;

import static net.jqwik.api.Tuple.Tuple2;

public class PropTestUtil {

    public static <INPUT, ANSWER> Function<INPUT, Tuple.Tuple2<INPUT, ANSWER>> packWithAnswer(
            Function<? super INPUT, ? extends ANSWER> f) {
        return input -> Tuple.of(input, f.apply(input));
    }

    public static <T1, T2> Tuple2<T2, T1> reverse(Tuple2<T1, T2> tuple) {
        return Tuple.of(tuple.get2(), tuple.get1());
    }
}
