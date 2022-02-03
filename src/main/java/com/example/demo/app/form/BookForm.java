package com.example.demo.app.form;

import lombok.Value;
import lombok.With;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Value
@With
public class BookForm {

    @Size(min = 1, max = 20)
    String name;

    @NotNull
    @PositiveOrZero
    Double price;
}
