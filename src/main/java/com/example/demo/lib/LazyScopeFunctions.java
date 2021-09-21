package com.example.demo.lib;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class LazyScopeFunctions<T> {

    public static <T, U> U with(Supplier<? extends T> supplier, Function<? super T, ? extends U> f) {
        return MonadicSupplier.of(supplier).map(f).get();
    }

    public static <T> T apply(Supplier<? extends T> supplier, Consumer<? super T> cb) {
        return MonadicSupplier.of(supplier).peek(cb).get();
    }

    private LazyScopeFunctions() {}
}
