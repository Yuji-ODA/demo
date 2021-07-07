package com.example.demo.lib;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface SupplierMonad<T> extends Supplier<T> {

    static <T> SupplierMonad<T> of(Supplier<T> supplier) {
        return supplier::get;
    }

    static <T> SupplierMonad<T> ofValue(T value) {
        return () -> value;
    }

    static <T, U, W> Function<T, SupplierMonad<W>> compose(Function<T, SupplierMonad<U>> f,
                                                           Function<U, SupplierMonad<W>> g) {
        return t -> g.apply(f.apply(t).get());
    }

    default <U> SupplierMonad<U> flatMap(Function<T, SupplierMonad<U>> f) {
        return map(f).get();
//        return () -> f.apply(get()).get();
//        return f.apply(get());
//        return compose(f, SupplierMonad::unit).apply(get());
    }

    default <U> SupplierMonad<U> map(Function<T, U> f) {
        return () -> f.apply(get());
//        return flatMap(t -> unit(f.apply(t)));
    }

    default <U, W> SupplierMonad<W> merge(SupplierMonad<U> other, BiFunction<T, U, W> f) {
        return flatMap(t -> other.map(u -> f.apply(t, u)));
    }
}
