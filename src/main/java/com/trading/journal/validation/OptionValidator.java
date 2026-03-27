package com.trading.journal.validation;

import com.trading.journal.model.TradeRequest;
import com.trading.journal.utility.FieldMapping;
import com.trading.journal.utility.FieldMapping.StrikePriceRange;
import com.trading.journal.validation.annotation.ValidOption;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OptionValidator implements ConstraintValidator<ValidOption, TradeRequest> {


    private final FieldMapping fieldMapping;  // Global mapping class

    public OptionValidator(FieldMapping fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    @Override
    public boolean isValid(TradeRequest request, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        String symbol = request.getSymbol();
        StrikePriceRange allowedRange = fieldMapping.getAllowedStrikePrice(symbol);
        boolean validStrikeRange = request.getStrikePrice() >= allowedRange.min() && request.getStrikePrice() <= allowedRange.max();
        boolean validStrikePrice = request.getStrikePrice() != null && request.getStrikePrice() > 0 && validStrikeRange;
        boolean validExchange = fieldMapping.validExchange(symbol, request.getExchange());
        if ("OPTIONS".equalsIgnoreCase(request.getInstrument())) {
            if (!validStrikePrice) {
                context.buildConstraintViolationWithTemplate("Strike Price must be inbound between range [" + allowedRange.min() + " , " + allowedRange.max() + "] for SYMBOL :" + symbol).addPropertyNode("strikePrice").addConstraintViolation();
            }
            if (!validExchange) {
                context.buildConstraintViolationWithTemplate("Invalid exchange provided for Symbol : " + symbol).addPropertyNode("exchange").addConstraintViolation();
            }
            if (StringUtils.isBlank(request.getOptionType())) {
                context.buildConstraintViolationWithTemplate("Option Type: [ CE/ PE ] must be provided for SYMBOL :" + symbol).addPropertyNode("optionType").addConstraintViolation();
            }
            return StringUtils.isNotBlank(request.getOptionType()) && validStrikePrice && validExchange;
        } else {
            return true;
        }
    }

}
