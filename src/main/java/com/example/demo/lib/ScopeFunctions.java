package com.example.demo.lib;

import java.util.function.Consumer;
import java.util.function.Function;

public final class ScopeFunctions {

    public static <T, U> U with(T t, Function<? super T, ? extends U> f) {
        return f.apply(t);
    }

    public static <T> T apply(T t, Consumer<? super T> cb) {
        cb.accept(t);
        return t;
    }

    private ScopeFunctions() {}
}
