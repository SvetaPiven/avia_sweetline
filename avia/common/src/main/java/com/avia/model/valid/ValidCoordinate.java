package com.avia.model.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@NotNull(message = "Coordinate value must not be null")
@Pattern(regexp = "-?\\d{1,3}\\.\\d{1,8}+", message = "Invalid coordinate format")
@ReportAsSingleViolation
public @interface ValidCoordinate {

    String message() default "Invalid coordinate format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}