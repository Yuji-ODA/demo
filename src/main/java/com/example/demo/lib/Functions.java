package com.example.demo.lib;

import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;

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

    public static Iterable<String> toLineIterable(BufferedReader reader) {

        try {
            String first = reader.readLine();

            return () -> new Iterator<>() {

                String cache = first;

                @Override
                public boolean hasNext() {
                    return cache != null;
                }

                @Override
                public String next() {
                    try {
                        String ret = cache;
                        cache = reader.readLine();
                        return ret;
                    } catch (IOException e) {
                        return null;
                    }
                }
            };
        } catch (IOException e2) {
            return Collections::emptyIterator;
        }
    }
}
