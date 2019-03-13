package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Value
public class GenericClass<T> {
    private final Class<T> parameterClass;
}
