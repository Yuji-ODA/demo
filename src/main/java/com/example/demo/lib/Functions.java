package com.example.demo.lib;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ByteArrayResource;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;

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

    public static void propertiesRecursiveEach(Object obj, BiConsumer<String, Object> callback) {
        propertiesRecursiveEachInternal(obj, new Stack<>(), callback);
    }

    private static void propertiesRecursiveEachInternal(Object obj, Stack<String> propertyNames,
                                                            BiConsumer<String, Object> callback) {

        BinaryOperator<String> reducer = (acc, elem) -> acc + '.' + elem;

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

            if (value != null && ByteArrayResource.class == value.getClass()) {
                value = value.toString();
            }
            propertiesRecursiveEachInternal(value, propertyNames, callback);

            propertyNames.pop();
        });
    }

}

