package com.example.demo.lib;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BinaryOperator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionalUtil {

    public static <T> Optional<T> reduce(T identity, BinaryOperator<T> f, Optional<T>... args) {
        return Arrays.stream(args).reduce(Optional.ofNullable(identity),
                (s, x) -> s.flatMap(a -> x.map(b -> f.apply(a, b))));
    }

    public static Optional<Integer> sumInt(Optional<Integer>... args) {
        return reduce(0, Integer::sum, args);
    }

    public static Optional<Long> sumLong(Optional<Long>... args) {
        return reduce(0L, Long::sum, args);
    }

    public static Optional<Double> sumDouble(Optional<Double>... args) {
        return reduce(0D, Double::sum, args);
    }

    public static <T> T[] tail(T[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        return Arrays.copyOfRange(array, 1, array.length);
    }
}
