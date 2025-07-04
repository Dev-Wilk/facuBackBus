package com.facu.backbus.validation;

import com.facu.backbus.dto.EventDTO;
import com.facu.backbus.model.Event;
import com.facu.backbus.repository.EventRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Validador para a anotação {@link BusScheduleFree}.
 * Verifica se um ônibus já está alocado em outro evento durante o período especificado.
 */
public class BusScheduleFreeValidator implements ConstraintValidator<BusScheduleFree, EventDTO> {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void initialize(BusScheduleFree constraintAnnotation) {
    }

    @Override
public boolean isValid(EventDTO eventDTO, ConstraintValidatorContext context) {
    if (eventDTO.getBusId() == null || eventDTO.getEventDepartureDate() == null || 
        eventDTO.getEventReturnDate() == null || eventDTO.getDepartureTime() == null || 
        eventDTO.getReturnTime() == null) {
        return true; // Deixa para outras anotações tratarem campos nulos
    }

    // Validação para eventos no mesmo dia
    if (eventDTO.getEventDepartureDate().equals(eventDTO.getEventReturnDate())) {
        if (eventDTO.getDepartureTime().isAfter(eventDTO.getReturnTime()) || 
            eventDTO.getDepartureTime().equals(eventDTO.getReturnTime())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                "Para eventos no mesmo dia, o horário de partida deve ser anterior ao horário de retorno.")
                .addConstraintViolation();
            return false;
        }
    }

    List<Event> conflictingEvents;
    if (eventDTO.getId() == null) {
        // Criação de novo evento
        conflictingEvents = eventRepository.findConflictingEventsByBus(
            eventDTO.getBusId(),
            eventDTO.getEventDepartureDate(),
            eventDTO.getEventReturnDate()
        );
    } else {
        // Atualização de evento existente
        conflictingEvents = eventRepository.findConflictingEventsByBus(
            eventDTO.getBusId(),
            eventDTO.getEventDepartureDate(),
            eventDTO.getEventReturnDate(),
            eventDTO.getId()
        );
    }

    return conflictingEvents.isEmpty();
}
}
