package com.example.demo.app.form;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class BookForm {

    @Size(min = 1, max = 20)
    String name;

    @NotNull
    Double price;
}
