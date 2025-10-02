package com.github.alinaguzova.projects.fitness_club_management_system.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface ValidEmail {

    String message() default "Email должен содержать @ и .";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
