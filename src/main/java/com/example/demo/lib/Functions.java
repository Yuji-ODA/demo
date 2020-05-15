package com.example.demo.lib;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.function.BiConsumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Functions {

    public static <T> void dumpProperties(T obj, BiConsumer<String, Object> callback) {
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

}

