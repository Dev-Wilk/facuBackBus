package com.facu.backbus.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {
    
    private String startDateFieldName;
    private String endDateFieldName;

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        this.startDateFieldName = constraintAnnotation.startDate();
        this.endDateFieldName = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field startDateField = value.getClass().getDeclaredField(startDateFieldName);
            startDateField.setAccessible(true);
            Field endDateField = value.getClass().getDeclaredField(endDateFieldName);
            endDateField.setAccessible(true);
            
            LocalDate startDate = (LocalDate) startDateField.get(value);
            LocalDate endDate = (LocalDate) endDateField.get(value);
            
            if (startDate == null || endDate == null) {
                return true; // Deixar que as anotações @NotNull tratem isso
            }
            
            return !startDate.isAfter(endDate);
            
        } catch (Exception e) {
            // Em caso de erro, retornar true para não mascarar outros erros de validação
            return true;
        }
    }
}
