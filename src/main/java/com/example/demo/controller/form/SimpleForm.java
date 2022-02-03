package com.example.demo.controller.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;

@Value(staticConstructor = "of")
@With
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SimpleForm<ADDRESS> {
    int id;
    String name;
    ADDRESS homeAddress;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXX")
    OffsetDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
    OffsetDateTime updatedAt;
}
