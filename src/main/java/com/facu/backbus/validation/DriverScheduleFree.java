package com.facu.backbus.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação de validação para garantir que um motorista não seja agendado para eventos 
 * com datas sobrepostas.
 * Esta anotação deve ser aplicada no nível da classe (em um DTO que contenha o ID do motorista 
 * e as datas do evento).
 * A validação é realizada pela classe {@link DriverScheduleFreeValidator}.
 */
@Constraint(validatedBy = DriverScheduleFreeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DriverScheduleFree {
    String message() default "Motorista já está alocado em outro evento neste período.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
