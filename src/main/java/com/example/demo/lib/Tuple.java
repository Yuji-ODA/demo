package com.example.demo.lib;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Tuple {
    @Value
    public static class Pair<T1, T2> {
        T1 _1;
        T2 _2;
    }

    @Value
    public static class Triple<T1, T2, T3> {
        T1 _1;
        T2 _2;
        T3 _3;
    }

    public static <T1, T2> Pair<T1, T2> tuple(T1 _1, T2 _2) {
        return new Pair<>(_1, _2);
    }

    public static <T1, T2, T3> Triple<T1, T2, T3> tuple(T1 _1, T2 _2, T3 _3) {
        return new Triple<>(_1, _2, _3);
    }
}
