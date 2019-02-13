package com.example.demo.lib;

import java.util.Arrays;
import java.util.Optional;

public abstract class OptionalUtil {

    public static Optional<Integer> sumOptInt(Optional<Integer>... args) {
        return sumOptIntArray(args);
    }

    public static Optional<Integer> sumOptIntArray(Optional<Integer>[] args) {
        if (0 < args.length) {
            Optional<Integer> head = args[0];
            Optional<Integer>[] tail = tail(args);
            return head.flatMap(x -> {
                return sumOptIntArray(tail).flatMap(y -> {
                    return Optional.of(x + y);
                });
            });
        }

        return Optional.of(0);
    }

    public static <T> T[] tail(T[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        return Arrays.copyOfRange(array, 1, array.length);
    }
}
