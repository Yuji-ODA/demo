package com.example.demo.lib;

import io.vavr.collection.List;

import java.util.function.Consumer;
import java.util.function.Function;

public class ScopeFunction<T> {

    public static <T, U> U with(T t, Function<? super T, ? extends U> f) {
        return of(t).map(f).value;
    }

    public static <T> T apply(T t, Consumer<? super T> cb) {
        return of(t).peek(cb).value;
    }

    private final T value;

    private ScopeFunction(T t) {
        this.value = t;
    }

    public static <T> ScopeFunction<T> of(T t) {
        return new ScopeFunction<>(t);
    }

    static <T> ScopeFunction<List<T>> sequence(List<ScopeFunction<T>> list) {
        return of(list.foldRight(List.empty(), (s, acc) -> acc.prepend(s.value)));
    }

    public T getValue() {
        return value;
    }

    public <U> ScopeFunction<U> map(Function<? super T, ? extends U> f) {
        return of(f.apply(value));
    }

    public ScopeFunction<T> peek(Consumer<? super T> consumer) {
        consumer.accept(value);
        return of(value);
    }

    public <U> ScopeFunction<U> flatMap(Function<? super T, ? extends ScopeFunction<U>> f) {
        return map(f).value;
    }
}
