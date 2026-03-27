package com.trading.journal.validation.annotation;

import com.trading.journal.validation.TradeParamsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TradeParamsValidator.class)
@Documented
public @interface ValidTradeParams {

    String message() default "Trade parameters invalid - check entry/target/stop/exit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
