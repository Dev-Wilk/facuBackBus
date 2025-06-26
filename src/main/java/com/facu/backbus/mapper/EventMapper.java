package com.facu.backbus.mapper;

import com.facu.backbus.dto.EventDTO;
import com.facu.backbus.model.Event;

public class EventMapper {
    public static EventDTO toDTO(Event event) {
        return new EventDTO(event);
    }
    public static Event toEntity(EventDTO dto) {
        Event event = new Event();
        event.setId(dto.getId());
        event.setResponsibleName(dto.getResponsibleName());
        event.setContactPhone(dto.getContactPhone());
        event.setEventLocation(dto.getEventLocation());
        event.setEventDepartureDate(dto.getEventDepartureDate());
        event.setEventReturnDate(dto.getEventReturnDate());
        event.setDepartureTime(dto.getDepartureTime());
        event.setReturnTime(dto.getReturnTime());
        event.setNumberOfPassengers(dto.getNumberOfPassengers());
        event.setEventValue(dto.getEventValue());
        // Note: employee, driver e bus precisam ser definidos separadamente
        return event;
    }
} 