package com.example.demo.lib;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public final class FunctionalUtil {

    private FunctionalUtil() {}

    public static <A, B> B foldLeft(Collection<A> collection, B identity, BiFunction<? super B, ? super A, ? extends B> f) {
        B value = identity;
        for (A a : collection) {
            value = f.apply(value, a);
        }
        return value;
    }

    public static <A, B> B foldRight(Collection<A> collection, B identity, BiFunction<? super A, ? super B, ? extends B> f) {
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

    public static <T1, T2, T3> Function<T1, T3> pipe(Function<T1, ? extends T2> f1,
                                                     Function<? super T2, T3> f2) {
        return f2.compose(f1);
    }

    public static <T1, T2, T3, T4> Function<T1, T4> pipe(Function<T1, ? extends T2> f1,
                                                         Function<? super T2, ? extends T3> f2,
                                                         Function<? super T3, T4> f3) {
        return f3.compose(f2.compose(f1));
    }

    public static <T, R> Function<Optional<T>, Optional<R>> lift(Function<? super T, ? extends R> f) {
        return t -> t.map(f);
    }

    public static <T1, T2, R> BiFunction<Optional<T1>, Optional<T2>, Optional<R>> lift(BiFunction<? super T1, ? super T2, ? extends R> f) {
        return (t1, t2) -> t1.flatMap(v1 -> t2.map(v2 -> f.apply(v1, v2)));
    }

    public static <T, R> Function<Optional<T>, R> argsLift(Function<? super T, ? extends R> f) {
        return pipe(lift(f), FunctionalUtil.<Optional<R>, R, R>curried(Optional::orElse), argApplier(null));
    }

    public static <T1, T2, R> Function<T1, Function<T2, R>> curried(BiFunction<? super T1, ? super T2, ? extends R> f) {
        return t1 -> t2 -> f.apply(t1, t2);
    }

    public static <T1, T2> Predicate<T1> pipe(Function<? super

            T1, ? extends T2> f, Predicate<? super T2> p) {
        return t1 -> p.test(f.apply(t1));
    }

    public static <T1, T2> Function<T1, Predicate<T2>> curried(BiPredicate<? super T1, ? super T2> bp) {
        return t1 -> t2 -> bp.test(t1, t2);
    }

    public static <T, R> Function<Function<T, R>, R> argApplier(T t) {
        return f -> f.apply(t);
    }

    public static Predicate<Object> isIdentical(Object o) {
        return supplied -> o == supplied;
    }
}
