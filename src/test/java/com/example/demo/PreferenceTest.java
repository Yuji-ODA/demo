package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = JacksonAutoConfiguration.class, initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(Preference.class)
//@TestPropertySource("classpath:application.yml")
public class PreferenceTest {

    @Autowired
    Preference preference;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void test() throws Exception {
        System.out.println(objectMapper.writeValueAsString(preference));
    }
}
