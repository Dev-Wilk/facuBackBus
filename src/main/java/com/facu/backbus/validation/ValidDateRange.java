package com.facu.backbus.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface ValidDateRange {
    String message() default "A data de partida deve ser anterior à data de retorno";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String startDate();
    String endDate();
    
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        ValidDateRange[] value();
    }
}
