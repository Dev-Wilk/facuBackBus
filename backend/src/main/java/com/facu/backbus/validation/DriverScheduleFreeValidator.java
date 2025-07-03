package com.facu.backbus.validation;

import com.facu.backbus.dto.EventDTO;
import com.facu.backbus.repository.EventRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import com.facu.backbus.model.Event;

/**
 * Validador para a anotação {@link DriverScheduleFree}.
 * Verifica se um motorista já está alocado em outro evento durante o período especificado.
 * A lógica diferencia a criação de um novo evento da atualização de um evento existente.
 */
public class DriverScheduleFreeValidator implements ConstraintValidator<DriverScheduleFree, EventDTO> {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void initialize(DriverScheduleFree constraintAnnotation) {
    }

    /**
     * Executa a lógica de validação.
     *
     * @param eventDTO O objeto DTO do evento que está sendo validado.
     * @param context O contexto no qual a validação está sendo executada.
     * @return {@code true} se o motorista estiver disponível no período especificado,
     * {@code false} caso contrário.
     */
    @Override
    public boolean isValid(EventDTO eventDTO, ConstraintValidatorContext context) {
        if (eventDTO.getDriverId() == null || eventDTO.getEventDepartureDate() == null || eventDTO.getEventReturnDate() == null) {
            return true; // Deixa para outras anotações tratarem campos nulos
        }

        List<Event> conflictingEvents;
        if (eventDTO.getId() == null) {
            // Criação de novo evento
            conflictingEvents = eventRepository.findConflictingEvents(
                eventDTO.getDriverId(),
                eventDTO.getEventDepartureDate(),
                eventDTO.getEventReturnDate()
            );
        } else {
            // Atualização de evento existente
            conflictingEvents = eventRepository.findConflictingEvents(
                eventDTO.getDriverId(),
                eventDTO.getEventDepartureDate(),
                eventDTO.getEventReturnDate(),
                eventDTO.getId()
            );
        }

        return conflictingEvents.isEmpty();
    }
}
