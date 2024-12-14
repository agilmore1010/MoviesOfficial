package com.example.movies1.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
public @interface ValidAge {
    String message() default "You must be older than 13 years old to register!";
    int minAge() default 13;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
