package com.example.movies1.validations;

import java.time.LocalDate;
import java.time.Period;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {

    private int minAge;

    @Override
    public void initialize(ValidAge constraintAnnotation){
        this.minAge = constraintAnnotation.minAge();
    }

    @Override
    public boolean isValid(LocalDate birthdate, ConstraintValidatorContext context) {
        if (birthdate == null) {
            return false; 
        }
        
        int age = Period.between(birthdate, LocalDate.now()).getYears();

        return age >= minAge;
    }
}
