package com.example.demo.controller.form;

import com.example.demo.controller.validation.ValidationTestFormValidator;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@ValidationTestFormValidator
public class ValidationTestForm {
    @Length(min = 1, max = 10)
    @NotNull
    private String name;

    @Range(min = 0, max = 100)
    @NotNull
    private Integer value1;

    @Range(min = 0, max = 200)
    @NotNull
    private Integer value2;
}
