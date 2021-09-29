package com.example.demo.app.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookForm {

    @Size(min = 1, max = 20)
    private String name;

    @NotNull
    private Double price;
}
