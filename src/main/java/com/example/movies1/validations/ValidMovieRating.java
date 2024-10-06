package com.example.movies1.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MovieRatingValidator.class)
public @interface ValidMovieRating {

    String message() default "Invalid movie rating";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
