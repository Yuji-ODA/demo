package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Configuration
public class AppConfig {
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder
                .mixIn(MultipartFile.class, IgnoreType.class)
                .modules(new VavrModule())
                .propertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
                .build();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .requestFactory(HttpComponentsClientHttpRequestFactory.class)
                .build();
    }

    @JsonIgnoreType
    public static class IgnoreType {}

    @Bean
    public GenericClass<String> genericClass() {
        return new GenericClass<>(String.class);
    }
}
