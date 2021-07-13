package com.example.demo.lib;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface MonadicSupplier<T> extends Supplier<T> {

    static <T> MonadicSupplier<T> of(Supplier<T> supplier) {
        return supplier::get;
    }

    static <T> MonadicSupplier<T> ofValue(T value) {
        return () -> value;
    }

    static <T, U, W> Function<T, MonadicSupplier<W>> compose(Function<T, MonadicSupplier<U>> f,
                                                             Function<U, MonadicSupplier<W>> g) {
        return t -> g.apply(f.apply(t).get());
    }

    default <U> MonadicSupplier<U> flatMap(Function<T, MonadicSupplier<U>> f) {
        return map(f).get();
//        return () -> f.apply(get()).get();
//        return f.apply(get());
//        return compose(f, SupplierMonad::unit).apply(get());
    }

    default <U> MonadicSupplier<U> map(Function<T, U> f) {
        return () -> f.apply(get());
//        return flatMap(t -> unit(f.apply(t)));
    }

    default <U, W> MonadicSupplier<W> merge(MonadicSupplier<U> other, BiFunction<T, U, W> f) {
        return flatMap(t -> other.map(u -> f.apply(t, u)));
    }
}
