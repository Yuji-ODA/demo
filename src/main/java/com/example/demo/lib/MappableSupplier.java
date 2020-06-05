package com.example.demo.lib;

import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface MappableSupplier<T> extends Supplier<T> {
    default <U> MappableSupplier<U> map(Function<T, U> f) {
        return () -> f.apply(get());
    }
}
