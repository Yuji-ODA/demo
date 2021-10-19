package com.example.demo.app.form;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;

@Value
@AllArgsConstructor(onConstructor = @__(@ConstructorProperties({"name", "price"})))
public class BookForm {

    @Size(min = 1, max = 20)
    String name;

    @NotNull
    Double price;
}
