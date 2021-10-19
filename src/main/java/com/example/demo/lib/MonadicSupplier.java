package com.example.demo.lib;

import io.vavr.collection.List;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface

MonadicSupplier<T> extends Supplier<T> {

    static <T> MonadicSupplier<T> of(Supplier<T> supplier) {
        return supplier::get;
    }

    static <T> MonadicSupplier<T> of(T value) {
        return () -> value;
    }

    static <T> MonadicSupplier<List<T>> sequence(List<? extends MonadicSupplier<T>> list) {
        return () -> list.foldRight(List.empty(), (m, acc) -> acc.prepend(m.get()));
    }

    static <T, U, W> Function<T, MonadicSupplier<W>> compose(Function<? super T, ? extends MonadicSupplier<U>> f,
                                                             Function<? super U, ? extends MonadicSupplier<W>> g) {
        return t -> g.apply(f.apply(t).get());
    }

    default <U> MonadicSupplier<U> flatMap(Function<? super T, ? extends MonadicSupplier<U>> f) {
        return map(f).get();
//        return f.apply(get());
//        return compose(f, SupplierMonad::unit).apply(get());
    }

    default <U> MonadicSupplier<U> map(Function<? super T, ? extends U> f) {
        return () -> f.apply(get());
//        return flatMap(t -> unit(f.apply(t)));
    }

    default <U, W> MonadicSupplier<W> merge(MonadicSupplier<U> other, BiFunction<T, U, W> f) {
        return flatMap(t -> other.map(u -> f.apply(t, u)));
    }

    default <U> MonadicSupplier<U> apply(MonadicSupplier<Function<? super T, ? extends U>> mtu) {
        return () -> mtu.get().apply(get());
    }

    default MonadicSupplier<T> peek(Consumer<? super T> consumer) {
        return () -> ScopeFunctions.apply(get(), consumer);
    }
}
