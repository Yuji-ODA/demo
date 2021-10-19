package com.example.demo.app.dto;

import lombok.Value;
import lombok.With;

@Value
@With
public class BookDto {
    String name;
    Double price;
}
