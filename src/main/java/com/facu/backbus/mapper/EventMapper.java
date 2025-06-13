package com.facu.backbus.mapper;

import com.facu.backbus.dto.EventDTO;
import com.facu.backbus.model.Event;

public class EventMapper {
    public static EventDTO toDTO(Event event) {
        return new EventDTO(event);
    }
    public static Event toEntity(EventDTO dto) {
        return dto.toEntity();
    }
} 
