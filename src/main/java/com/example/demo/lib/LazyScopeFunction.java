package com.example.demo.lib;

import io.vavr.collection.List;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface LazyScopeFunction<T> extends Supplier<T> {

    static <T> LazyScopeFunction<T> ofSupplier(Supplier<T> supplier) {
        return supplier::get;
    }

    static <T> LazyScopeFunction<T> of(T t) {
        return () -> t;
    }

    static <T, U> U with(T t, Function<? super T, ? extends U> f) {
        return withOfSupplier(of(t), f);
    }

    static <T> T apply(T t, Consumer<? super T> cb) {
        return applyOfSupplier(of(t), cb);
    }

    static <T, U> U withOfSupplier(Supplier<T> supplier, Function<? super T, ? extends U> f) {
        return ofSupplier(supplier).map(f).get();
    }

    static <T> T applyOfSupplier(Supplier<T> supplier, Consumer<? super T> cb) {
        return ofSupplier(supplier).peek(cb).get();
    }

    static <T> LazyScopeFunction<List<T>> sequence(List<LazyScopeFunction<T>> list) {
        return () -> list.foldRight(List.empty(), (s, acc) -> acc.prepend(s.get()));
    }

    default <U> LazyScopeFunction<U> map(Function<? super T, ? extends U> f) {
        return () -> f.apply(get());
    }

    default <U> LazyScopeFunction<U> flatMap(Function<? super T, ? extends LazyScopeFunction<U>> f) {
        return () -> map(f).get().get();
    }

    default LazyScopeFunction<T> peek(Consumer<? super T> consumer) {
        return () -> {
            T t = get();
            consumer.accept(t);
            return t;
        };
    }
}
