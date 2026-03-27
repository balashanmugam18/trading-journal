package com.trading.journal.validation.annotation;

import com.trading.journal.validation.OptionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Constraint(validatedBy = OptionValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOption {
    String message() default "OptionType : CALL/PUT and StrikePrice is mandatory for OPTIONS trade.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
