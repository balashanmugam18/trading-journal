package com.trading.journal.validation;

import com.trading.journal.utility.FieldMapping;
import com.trading.journal.validation.annotation.ValidField;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class FieldValidator implements ConstraintValidator<ValidField, String> {

    private final FieldMapping fieldMapping;  // Global mapping class

    private String fieldName;

    public FieldValidator(FieldMapping fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    @Override
    public void initialize(ValidField constraintAnnotation) {
        this.fieldName = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        List<String> allowedList = fieldMapping.getAllowedList(fieldName);
        if (CollectionUtils.isEmpty(allowedList)){
            log.warn("Field '{}' not mapped in FieldMapping", fieldName);
            return false;
        }else {
            return allowedList.contains(fieldValue);
        }
    }
}
