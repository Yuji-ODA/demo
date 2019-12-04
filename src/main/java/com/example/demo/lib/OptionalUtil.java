package com.example.demo.lib;

import java.util.Arrays;
import java.util.Optional;

public abstract class OptionalUtil {

    public static Optional<Integer> sumOptInt(Optional<Integer>... args) {
        if (0 < args.length) {
            Optional<Integer> head = args[0];
            Optional<Integer>[] tail = tail(args);
            return head.flatMap(x -> sumOptInt(tail).map(y -> x + y));
        }

        return Optional.of(0);
    }

    public static Optional<Integer> sumOptIntReduce(Optional<Integer>... args) {
        return Arrays.stream(args).reduce(Optional.of(0), (s,x) -> s.flatMap(a -> x.map(b -> a + b)));
    }


    public static <T> T[] tail(T[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        return Arrays.copyOfRange(array, 1, array.length);
    }
}
