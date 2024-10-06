package com.example.movies1.validations;

import java.util.Calendar;
import java.util.Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<ValidAge, Date> {

    @Override
    public boolean isValid(Date birthdate, ConstraintValidatorContext context) {
        if (birthdate == null) {
            return true; // Let other validations handle null checks
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthdate);
        calendar.add(Calendar.YEAR, 10); // Add 10 years to the birthdate

        return calendar.before(new Date()); // Check if the date is before today
    }
}
