package com.example.demo.lib;

import lombok.Value;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Value(staticConstructor = "of")
public class IndexedValue<T> {
    long index;
    T value;

    public static <T> Function<T, IndexedValue<T>> withIndexFrom(long start, long step) {
        AtomicLong index = new AtomicLong(start);
        return t -> IndexedValue.of(index.getAndAdd(step), t);
    }

    public static <T> Function<T, IndexedValue<T>> withIndexFrom(long start) {
        return withIndexFrom(start, 1);
    }

    public static <T> Function<T, IndexedValue<T>> withIndex() {
        return withIndexFrom(0);
    }
}
