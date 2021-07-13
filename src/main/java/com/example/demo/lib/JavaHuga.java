package com.example.demo.lib;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor(staticName = "of")
public class JavaHuga {
    private int id;

//    @JsonProperty("c_value")
    private float cValue;

    private String beType;
}
