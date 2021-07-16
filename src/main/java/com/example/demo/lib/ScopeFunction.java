package com.example.demo.lib;

import io.vavr.collection.List;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface ScopeFunction<T> extends Supplier<T> {

    static <T> ScopeFunction<T> with(Supplier<T> supplier) {
        return supplier::get;
    }

    static <T> ScopeFunction<T> apply(Supplier<? extends T> supplier, Consumer<? super T> consumer) {
        return () -> {
            T t = supplier.get();
            consumer.accept(t);
            return t;
        };
    }

    static <T> ScopeFunction<List<T>> sequence(List<ScopeFunction<T>> list) {
        return () -> list.foldRight(List.empty(), (s, acc) -> acc.prepend(s.get()));
    }

    default <U> U eval(Function<? super T, ? extends U> f) {
        return f.apply(get());
    }

    default <U> ScopeFunction<U> map(Function<? super T, ? extends U> f) {
        return () -> eval(f);
    }
}
