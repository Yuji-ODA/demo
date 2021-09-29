package com.example.demo.controller.validation;

import com.example.demo.controller.form.ValidationTestForm;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, TYPE, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidationTestFormValidator.Validator.class)
public @interface ValidationTestFormValidator {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ValidationTestFormValidator, ValidationTestForm> {

        @Override
        public boolean isValid(ValidationTestForm value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }

            if (200 < value.getValue1() + value.getValue2()) {
                context.buildConstraintViolationWithTemplate("足して200オーバーはダメ")
                        .addPropertyNode("value1")
                        .addConstraintViolation();
                return false;
            }

            return true;
        }
    }


}
