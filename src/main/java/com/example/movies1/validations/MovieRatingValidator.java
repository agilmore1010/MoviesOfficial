package com.example.movies1.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MovieRatingValidator implements ConstraintValidator<ValidMovieRating, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null){
            return false;
        }
        return value.equals("G") || value.equals("PG") || value.equals("PG-13") || value.equals("R") || value.equals("NC-17") || value.equals("Unrated")||
        value.equals("TV-Y") || value.equals("TV-Y7") || value.equals("TV-Y7-FV") || value.equals("TV-G") || value.equals("TV-PG")|| value.equals("TV-14") || value.equals("TV-MA");
    }
}

