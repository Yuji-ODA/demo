package com.example.demo.controller.form;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(JacksonAutoConfiguration.class)
class SimpleFormTest {

    @Autowired
    Jackson2ObjectMapperBuilder builder;

    ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = builder.build();
    }

    @Test
    void serialize() throws Exception {
        OffsetDateTime given = OffsetDateTime.of(2021, 9, 22, 9, 0, 0, 0, ZoneOffset.ofHours(9));
        SimpleForm<String> target = SimpleForm.of(1, "ダミー", "ｍｙFile", given, given);
        assertThat(mapper.writeValueAsString(target))
                .isEqualTo("{\"id\":1,\"name\":\"ダミー\",\"home_address\":\"ｍｙFile\",\"created_at\":\"2021-09-22T09:00:00.000000+0900\",\"updated_at\":\"2021-09-22T09:00:00.000000+09:00\"}");
    }

    @Test
    void deserialize() throws Exception {
        String serialized = "{\"id\":1,\"name\":\"ダミー\",\"home_address\":\"ｍ" +
                "ｙFile\",\"created_at\":\"2020-05-31T00:00:00.000000+0900\",\"updated_at\":\"2021-09-21T14:56:21.062734+09:00\"}";
        SimpleForm<String> actual = mapper.readValue(serialized, new TypeReference<>() {});
        assertThat(actual).hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "ダミー")
                .hasFieldOrPropertyWithValue("homeAddress", "ｍｙFile")
                .hasFieldOrPropertyWithValue("createdAt", OffsetDateTime.of(2020, 5, 30, 15, 0, 0, 0, ZoneOffset.UTC))
                .hasFieldOrPropertyWithValue("updatedAt", OffsetDateTime.of(2021, 9, 21, 5, 56, 21, 62_734_000, ZoneOffset.UTC));
    }
}