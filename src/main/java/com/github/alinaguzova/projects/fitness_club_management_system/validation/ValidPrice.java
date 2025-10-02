package com.github.alinaguzova.projects.fitness_club_management_system.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PriceValidator.class)
public @interface ValidPrice {

    public String message() default "Цена от 0 до 999,99";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};

}
