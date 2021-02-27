package com.example.demo.lib;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class F {
    public static <T> UnaryOperator<T> unary(UnaryOperator<T> fun) {
        return fun;
    }

    public static <T, R> Function<T, R> function(Function<T, R> fun) {
        return fun;
    }

}
