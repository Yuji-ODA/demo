package com.example.demo.lib;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptionalUtil {

    @SafeVarargs
    public static <T> Optional<T> reduce(T identity, BinaryOperator<T> f, Optional<T>... args) {
        return Arrays.stream(args).reduce(Optional.ofNullable(identity),
                (s, x) -> s.flatMap(a -> x.map(b -> f.apply(a, b))));
    }

    public static <T> Optional<T> reduce(T identity, BinaryOperator<T> f, Collection<Optional<T>> args) {
        return args.stream().reduce(Optional.ofNullable(identity),
                (s, x) -> s.flatMap(a -> x.map(b -> f.apply(a, b))));
    }

    public static Optional<Integer> sumInt(Optional<Integer>... args) {
        return reduce(0, Integer::sum, args);
    }

    public static Optional<Long> sumLong(Optional<Long>... args) {
        return reduce(0L, Long::sum, args);
    }

    public static Optional<Double> sumDouble(Optional<Double>... args) {
        return reduce(0D, Double::sum, args);
    }

    public static <T> T[] tail(T[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        return Arrays.copyOfRange(array, 1, array.length);
    }

    public static <A, B, C> Optional<C> map2(Optional<A> aOptional, Optional<B> bOptional, BiFunction<A, B, C> f) {
        return aOptional.flatMap(a -> bOptional.map(b -> f.apply(a, b)));
    }

    public static <A> Optional<List<A>> sequence(List<Optional<A>> l) {
        return traverse(l, Optional::of);
    }

    public static <A, B> Optional<List<B>> traverse(List<Optional<A>> l, Function<A, Optional<B>> f) {
        return FunctionalUtil.foldRight(l, Optional.of(new ArrayList<>()),
                (aOptional, acc) -> map2(aOptional.flatMap(f), acc,
                        (b, bList) -> {
                            bList.add(0, b);
                            return bList;
                        }));
    }
}
