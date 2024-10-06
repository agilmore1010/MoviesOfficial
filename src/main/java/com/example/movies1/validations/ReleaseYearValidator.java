package com.example.movies1.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReleaseYearValidator implements ConstraintValidator<ValidReleaseYear, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // or return true if null is acceptable

                }return value >= 1888 && value <= java.time.Year.now().getValue(); // Check if it's a valid 4-digit year
    }
}
