package com.example.demo.lib;

import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.flywaydb.core.internal.util.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Functions {

    public static void propertiesEach(Object obj, BiConsumer<String, Object> callback) {

        if (obj == null || BeanUtils.isSimpleValueType(obj.getClass())) {
            callback.accept("", obj);
            return;
        }

        Arrays.stream(BeanUtils.getPropertyDescriptors(obj.getClass())).forEach(descriptor -> {
            String propertyName = descriptor.getName();

            if ("class".equals(propertyName)) {
                return;
            }

            try {
                callback.accept(propertyName, descriptor.getReadMethod().invoke(obj));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void eachProperty(Object obj, BiConsumer<String, Object> callback) {
        eachPropertyInternal(obj, new Stack<>(), callback);
    }

    private static void eachPropertyInternal(Object obj, Stack<String> propertyNames, BiConsumer<String, Object> callback) {

        final BinaryOperator<String> reducer = (acc, elem) -> acc + '.' + elem;

        if (obj == null || BeanUtils.isSimpleProperty(obj.getClass()) || obj instanceof Collection || obj instanceof Map) {
            callback.accept(propertyNames.stream().reduce(reducer).orElse(""), obj);
            return;
        }

        Arrays.stream(BeanUtils.getPropertyDescriptors(obj.getClass())).forEach(descriptor -> {
            String propertyName = descriptor.getName();

            if ("class".equals(propertyName)) {
                return;
            }

            Object value;
            try {
                value = descriptor.getReadMethod().invoke(obj);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            propertyNames.push(propertyName);
            eachPropertyInternal(value, propertyNames, callback);
            propertyNames.pop();
        });
    }

    public static boolean isValidUUID(@Nullable String input) {
        return Optional.ofNullable(input)
//                .map(Try::success)
//                .map(liftTry(UUID::fromString))
//                .map(i -> Try.ofSupplier(() -> UUID.fromString(i)))
                .<CheckedFunction0<UUID>>map(i -> () -> UUID.fromString(i))
                .map(Try::of)
                .map(Try::isSuccess)
                .orElse(true);
    }

    private static <T, R> Function<Try<T>, Try<R>> liftTry(Function<? super T, ? extends R> f) {
        return tr -> tr.map(f);
    }

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
        Map<K, List<V>> merged = new HashMap<>(map1);
        map2.forEach((key, value) ->
                merged.merge(key, value, (origValue, newValue) -> {
                    origValue.addAll(newValue);
                    return origValue;
                })
        );
        return merged;
    }

    public static <T> List<List<T>> partition(List<T> list, int targetSize) {
        if (targetSize <= 0) {
            throw new IllegalArgumentException("length must be positive");
        }

        return IntStream.iterate(0, i -> i + targetSize)
                .limit((int)Math.ceil((double)list.size()/targetSize))
                .mapToObj(i -> list.subList(i, Math.min(i + targetSize, list.size())))
                .collect(Collectors.toList());
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
                        (acc, elem) -> acc.getRight().size() < targetSize ?
                                Pair.of(acc.getLeft(), addToList.apply(acc.getRight(), elem)) :
                                Pair.of(Stream.concat(acc.getLeft(), Stream.of(acc.getRight())), new ArrayList<>(targetSize)),
                        (a1, a2) -> Pair.of(Stream.concat(a1.getLeft(), a2.getLeft()), a1.getRight()));

        return x.getRight().isEmpty() ? x.getLeft() : Stream.concat(x.getLeft(), Stream.of(x.getRight()));
    }
}
