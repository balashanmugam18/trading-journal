package com.trading.journal.validation.annotation;


import com.trading.journal.validation.FieldValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldValidator.class)
@Documented
public @interface ValidField {

    String field();

    String message() default "Invalid value provided for {field}" ;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
