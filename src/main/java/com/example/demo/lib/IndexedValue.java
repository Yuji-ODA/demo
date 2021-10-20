package com.example.demo.lib;

import lombok.Value;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Value(staticConstructor = "of")
public class IndexedValue<T> {
    long index;
    T value;

    public static <T> Function<T, IndexedValue<T>> zipWithIndex(long start) {
        AtomicLong index = new AtomicLong(start);
        return t -> IndexedValue.of(index.getAndIncrement(), t);
    }

    public static <T> Function<T, IndexedValue<T>> zipWithIndex() {
        return zipWithIndex(0);
    }
}
