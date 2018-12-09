package com.example.demo.util;

import lombok.Value;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ImmutableMap {

    @NonNull
    public static <K, V> Map<K, V> of(final K key, final V value) {
        return umap(Pair.of(key, value));
    }

    @NonNull
    public static <K, V> Map<K, V> of(final K key1, final V value1, final K key2, final V value2) {
        return umap(Pair.of(key1, value1), Pair.of(key2, value2));
    }

    @NonNull
    public static <K, V> Map<K, V> of(final K key1, final V value1, final K key2, final V value2, final K key3, final V value3) {
        return umap(Pair.of(key1, value1), Pair.of(key2, value2), Pair.of(key3, value3));
    }


    private static <K, V> Map<K, V> umap(Pair<K, V>... pairs) {
        return Collections.unmodifiableMap(
                Arrays.stream(pairs).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)));
    }

    @Value(staticConstructor = "of")
    static class Pair<A, B> {

        private final A first;
        private final B second;
    }
}
