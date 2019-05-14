package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
public class GenericClass<T> {
    private final Class<T> parameterClass;
}
