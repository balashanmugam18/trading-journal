package com.trading.journal.validation;

import com.trading.journal.model.TradeRequest;
import com.trading.journal.validation.annotation.ValidTradeParams;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.*;

public class TradeParamsValidator implements ConstraintValidator<ValidTradeParams, TradeRequest> {

    @Override
    public boolean isValid(TradeRequest request, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        boolean validTarget = validTarget(request, context);
        boolean validStopLoss = validStopLoss(request, context);
        boolean validExit = validExit(request, context);

        return validExit && validTarget && validStopLoss;
    }

    private boolean validExit(TradeRequest request, ConstraintValidatorContext context) {
        final Double exit = request.getExitValue();
        final Double target = request.getTargetValue();
        final Double stopLoss = request.getStopLossValue();
        final Double breakEven = request.getEntryValue();
        List<Double> valueList = Arrays.asList(target, stopLoss, breakEven);

        boolean validExit = valueList.contains(exit);
        if (!validExit) {

            context.buildConstraintViolationWithTemplate("Exit: " + exit + " must match one of  [ break even:" + breakEven + "/ target:" + target + "/ stop loss: " + stopLoss + "]" + " or consider as NO TRADE.").addPropertyNode("exitValue").addConstraintViolation();
        }
        return validExit;
    }

    private boolean validTarget(TradeRequest request, ConstraintValidatorContext context) {
        final Double target = request.getTargetValue();
        final Double entry = request.getEntryValue();
        boolean validTarget = target > entry && (Math.abs(target - entry) >= 1);
        if (!validTarget) {
            context.buildConstraintViolationWithTemplate("Target (" + target + ") must be greater than entry: " + entry + "by atleast > 1 point.").addPropertyNode("targetValue").addConstraintViolation();
        }
        return validTarget;
    }

    private boolean validStopLoss(TradeRequest request, ConstraintValidatorContext context) {
        final Double stopLoss = request.getStopLossValue();
        final Double entry = request.getEntryValue();
        boolean validStopLoss = stopLoss <= entry && (Math.abs(entry - stopLoss) >= 0);
        if (!validStopLoss) {
            context.buildConstraintViolationWithTemplate("StopLoss (" + stopLoss + ") must be less than or equal to entry: " + entry).addPropertyNode("stopLossValue").addConstraintViolation();
        }
        return validStopLoss;
    }

}
