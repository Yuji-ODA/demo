package com.example.demo.controller.converter;

import com.example.demo.controller.form.MyClass;
import com.example.demo.controller.form.MyClass2;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Converters {
    public static <T> Function<String, T> convert(ObjectMapper mapper, TypeReference<T> typeReference) {
        return value -> {
            try {
                return mapper.readValue(value, typeReference);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Component
    @RequiredArgsConstructor
    public static class StringToMyClass2ListConverter implements Converter<String, List<MyClass2>> {

        private final ObjectMapper mapper;

        @Override
        public List<MyClass2> convert(String source) {
            return Converters.convert(mapper, new TypeReference<List<MyClass2>>() {}).apply(source);
        }
    }

    @Component
    @RequiredArgsConstructor
    public static class StringToMyClassConverter implements Converter<String, MyClass> {

        private final ObjectMapper mapper;

        @Override
        public MyClass convert(String source) {
            return Converters.convert(mapper, new TypeReference<MyClass>() {}).apply(source);
        }
    }
}
