package com.example.demo.lib;

import org.apache.logging.log4j.util.Supplier;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface SupplierMonad<T> extends Supplier<T> {

    static <T> SupplierMonad<T> unit(T value) {
        return () -> value;
    }

    static <T> SupplierMonad<T> unit(Supplier<T> supplier) {
        return supplier::get;
    }

    static <T, U, W> Function<T, SupplierMonad<W>> compose(Function<T, SupplierMonad<U>> f, Function<U, SupplierMonad<W>> g) {
        return t -> g.apply(f.apply(t).get());
    }

    static <T> SupplierMonad<T> join(SupplierMonad<SupplierMonad<T>> mmt) {
//        return mmt.flatMap(Function.identity());
        return mmt.get();
    }

    default <U> SupplierMonad<U> flatMap(Function<T, SupplierMonad<U>> f) {
//        return compose(f, SupplierMonad::unit).apply(get());
        return f.apply(get());
    }

    default <U> SupplierMonad<U> map(Function<T, U> f) {
        return flatMap(t -> unit(f.apply(t)));
    }

    default <U, W> SupplierMonad<W> merge(SupplierMonad<U> other, BiFunction<T, U, W> f) {
        return flatMap(t -> other.map(u -> f.apply(t, u)));
    }
}
