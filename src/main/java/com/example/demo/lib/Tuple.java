package com.example.demo.lib;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Tuple {
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class Pair<T1, T2> {
        public final T1 _1;
        public final T2 _2;

        public T1 get1() {
            return _1;
        }

        public T2 get2() {
            return _2;
        }
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class Triple<T1, T2, T3> {
        public final T1 _1;
        public final T2 _2;
        public final T3 _3;

        public T1 get1() {
            return _1;
        }

        public T2 get2() {
            return _2;
        }

        public T3 get3() {
            return _3;
        }
    }

    public static <T1, T2> Pair<T1, T2> tuple(T1 _1, T2 _2) {
        return new Pair<>(_1, _2);
    }

    public static <T1, T2, T3> Triple<T1, T2, T3> tuple(T1 _1, T2 _2, T3 _3) {
        return new Triple<>(_1, _2, _3);
    }
}
