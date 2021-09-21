package com.example.demo.controller.form;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import static com.example.demo.util.DateTimeUtil.offsetDateTime2LocalDateTime;

@ExtendWith(SpringExtension.class)
@Import(Jackson2ObjectMapperBuilder.class)
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
        OffsetDateTime now = OffsetDateTime.now();
        SimpleForm target = new SimpleForm(1, "ダミー", "ｍｙFile", now, now);
        mapper.writeValue(System.out, target);
    }

    @Test
    void deserialize() throws Exception {
        String serialized = "{\"id\":1,\"name\":\"ダミー\",\"home_address\":\"ｍ" +
                "ｙFile\",\"created_at\":\"2021-09-21T14:56:21.062734+09:00\",\"updated_at\":\"2021-09-21T14:56:21.062734+09:00\"}";
        SimpleForm form = mapper.readValue(serialized, SimpleForm.class);
        System.out.println(form);
        System.out.println(offsetDateTime2LocalDateTime(form.getCreatedAt()));
        System.out.println(offsetDateTime2LocalDateTime(form.getUpdatedAt()));
        System.out.println(ZonedDateTime.now().toLocalDateTime());
    }
}