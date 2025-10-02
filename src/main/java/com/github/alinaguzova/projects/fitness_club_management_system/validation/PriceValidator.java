package com.github.alinaguzova.projects.fitness_club_management_system.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class PriceValidator implements ConstraintValidator<ValidPrice, BigDecimal> {
    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return true;

        BigDecimal min = new BigDecimal("0.00");
        BigDecimal max = new BigDecimal("999.99");

        if(value.compareTo(min) <= 0 || value.compareTo(max) > 0){
            return false;
        }

        int integerPart = value.precision() - value.scale();
        int fractionPart = value.scale();

        return integerPart <= 3 && fractionPart <= 2;
    }
}
