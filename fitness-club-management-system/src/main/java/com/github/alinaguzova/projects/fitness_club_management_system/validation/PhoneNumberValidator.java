package com.github.alinaguzova.projects.fitness_club_management_system.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    public static final Pattern PATTERN = Pattern.compile("\\+375(29|33|44|25|17)[0-9]{7}");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }
        return PATTERN.matcher(value).matches();
    }

}
