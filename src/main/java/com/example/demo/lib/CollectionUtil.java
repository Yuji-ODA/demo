package com.example.demo.lib;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionUtil {

    /**
     * ListをvalueとするMapに値を追加する
     *
     * @param map 値を設定するMap
     * @param key 設定するキー
     * @param value 追加する値
     * @param <K> key type
     * @param <V> value type
     */
    public static <K, V> void appendValue(Map<K, List<V>> map, K key, V value) {
        List<V> list = map.getOrDefault(key, new ArrayList<>());
        list.add(value);
        map.put(key, list);
    }

    private static <K, V> BiFunction<Map<K, List<V>>, V, Map<K, List<V>>> valueAppender(K key) {
        return (map, value) -> {
            appendValue(map, key, value);
            return map;
        };
    }

    /**
     * Listをvalueとする2つのMapをマージする
     *
     * @param map1 map
     * @param map2 map
     * @param <K> key type
     * @param <V> value type
     * @return マージしたmap
     */
    public static <K, V> Map<K, List<V>> mergeListMap(Map<K, List<V>> map1, Map<K, List<V>> map2) {
        return merge(map1, map2, CollectionUtil::concat);
    }

    public static <T> List<T> concat(List<T> list1, List<T> list2) {
        return new ArrayList<>(list1) {{
            addAll(list2);
        }};
    }

    public static <K, V> Map<K, V> merge(Map<K, V> map1, Map<K, V> map2, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Map<K, V> merging = new HashMap<>(map1);
        map2.forEach((key, value) -> merging.merge(key, value, remappingFunction));
        return merging;
    }

    public static <K, V> Map<K, V> merge(Map<K, V> map1, Map<K, V> map2) {
        return merge(map1, map2, (value1, value2) -> value1);
    }

    public static <K, V> Map<K, V> mergeAlt(Map<K, V> map1, Map<K, V> map2, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return map2.entrySet().stream()
                .<Map<K, V>>reduce(new HashMap<>(map1),
                        (map, entry) -> {
                            map.merge(entry.getKey(), entry.getValue(), remappingFunction);
                            return map;
                        }, (m1, m2) -> CollectionUtil.mergeAlt(m1, m2, remappingFunction));
    }

    public static <K, V> Map<K, List<V>> mergeListMapAlt(Map<K, List<V>> map1, Map<K, List<V>> map2) {
        return map2.entrySet().stream()
                .<Map<K, List<V>>>reduce(new HashMap<>(map1),
                        (map, entry) -> {
                            map.merge(entry.getKey(), entry.getValue(), CollectionUtil::concat);
                            return map;
                        }, CollectionUtil::mergeListMapAlt);
    }

    public static <T> List<List<T>> partition(List<T> list, int targetSize) {
        if (targetSize <= 0) {
            throw new IllegalArgumentException("length must be positive");
        }

        return IntStream.iterate(0, i -> i + targetSize)
                .limit((int)Math.ceil((double)list.size()/targetSize))
                .mapToObj(i -> list.subList(i, Math.min(i + targetSize, list.size())))
                .toList();
    }

    public static <T> Stream<List<T>> partition(Stream<T> stream, int targetSize) {
        if (targetSize <= 0) {
            throw new IllegalArgumentException("length must be positive");
        }

        BiFunction<List<T>, T, List<T>> addToList = (list, value) -> {
            list.add(value);
            return list;
        };

        Pair<Stream<List<T>>, List<T>> x = stream
                .reduce(Pair.of(Stream.empty(), new ArrayList<>(targetSize)),
                        (acc, elem) -> acc.getSecond().size() < targetSize ?
                                Pair.of(acc.getFirst(), addToList.apply(acc.getSecond(), elem)) :
                                Pair.of(Stream.concat(acc.getFirst(), Stream.of(acc.getSecond())), new ArrayList<>(targetSize)),
                        (a1, a2) -> Pair.of(Stream.concat(a1.getFirst(), a2.getFirst()), a1.getSecond()));

        return x.getSecond().isEmpty() ? x.getFirst() : Stream.concat(x.getFirst(), Stream.of(x.getSecond()));
    }

    public static <T> Stream<T> iterableToStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <T> Stream<T> iteratorToStream(Iterator<T> iterator) {
        return iterableToStream(() -> iterator);
    }

    public static Iterable<Integer> range(int start, int stop) {
        return IntStream.range(start, stop)::iterator;
    }

    public static Iterable<Long> range(long start, long stop) {
        return LongStream.range(start, stop)::iterator;
    }
}
