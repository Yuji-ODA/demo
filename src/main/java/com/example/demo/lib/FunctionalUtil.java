package com.example.demo.lib;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FunctionalUtil {

    public static <A, B> B foldLeft(@lombok.NonNull List<A> l, B identity, BiFunction<B, A, B> f) {
        B value = identity;
        for (A a : l) {
            value = f.apply(value, a);
        }
        return value;
    }

    public static <A, B> B foldRight(@lombok.NonNull List<A> l, B identity, BiFunction<A, B, B> f) {
        return foldLeft(l, Function.<B>identity(), (fb, a) -> b -> fb.apply(f.apply(a, b))).apply(identity);
    }
}
