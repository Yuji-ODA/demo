package com.example.demo.app.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BookForm {

    @Size(min = 1, max = 20)
    private String name;

    @NotNull
    private Double price;
}
