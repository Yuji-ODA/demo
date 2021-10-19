package com.example.demo.lib;

import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
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
        Set<K> allKeys = Stream.of(map1, map2)
                        .map(Map::keySet)
                        .reduce(new HashSet<>(), (acc, set) -> {
                            acc.addAll(set);
                            return acc;
                        });

        Map<K, List<V>> merged = new HashMap<>(map1);
        for (K key : allKeys) {
            if (merged.containsKey(key)) {
                merged.get(key).addAll(map2.getOrDefault(key, Collections.emptyList()));
            } else if (map2.containsKey(key)) {
                merged.put(key, map2.get(key));
            }
        }
        return merged;
    }
}
