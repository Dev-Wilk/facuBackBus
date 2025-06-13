package com.facu.backbus.service;

import com.facu.backbus.model.Event;
import com.facu.backbus.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Transactional
    public Event save(Event event) {
        // Salva o evento (os relacionamentos serão gerenciados automaticamente pelo JPA)
        return eventRepository.save(event);
    }

    @Transactional
    public void deleteById(Long id) {
        // Remove o evento (como os relacionamentos são ManyToOne, não precisamos removê-los manualmente)
        eventRepository.deleteById(id);
    }
}
