package com.example.demo.lib;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FunctionalUtil {

    public static <A, B> B foldLeft(@lombok.NonNull Collection<A> collection, B identity, BiFunction<B, A, B> f) {
        B value = identity;
        for (A a : collection) {
            value = f.apply(value, a);
        }
        return value;
    }

    public static <A, B> B foldRight(@lombok.NonNull Collection<A> collection, B identity, BiFunction<A, B, B> f) {
        if (collection instanceof List) {
            List<A> list = (List<A>) collection;
            B value = identity;
            for (int i = list.size() - 1; 0 <= i; --i) {
                value = f.apply(list.get(i), value);
            }
            return value;
        }

        // may occur stack overflow
        return foldLeft(collection, Function.<B>identity(), (fb, a) -> b -> fb.apply(f.apply(a, b))).apply(identity);
    }
}
