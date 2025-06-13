package com.facu.backbus.controller;

import com.facu.backbus.dto.EventDTO;
import com.facu.backbus.mapper.EventMapper;
import com.facu.backbus.model.Event;
import com.facu.backbus.model.User;
import com.facu.backbus.service.EventService;
import com.facu.backbus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.facu.backbus.model.Driver;
import com.facu.backbus.model.Bus;
import com.facu.backbus.service.DriverService;
import com.facu.backbus.service.BusService;

/**
 * Controlador responsável por gerenciar operações relacionadas a eventos.
 * Permite listar, buscar por ID, criar, atualizar e deletar eventos.
 */
@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "*")
public class EventController {
    
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    
    private final EventService eventService;
    private final UserService userService;
    private final DriverService driverService;
    private final BusService busService;

    @Autowired
    public EventController(EventService eventService, UserService userService, 
                         DriverService driverService, BusService busService) {
        this.eventService = eventService;
        this.userService = userService;
        this.driverService = driverService;
        this.busService = busService;
    }


    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.findAll().stream()
                .map(EventMapper::toDTO)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return eventService.findById(id)
                .map(EventMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        try {
            logger.info("Iniciando criação de evento: {}", eventDTO);
            

            Event event = new Event();
            event.setResponsibleName(eventDTO.getResponsibleName());
            event.setContactPhone(eventDTO.getContactPhone());
            event.setEventLocation(eventDTO.getEventLocation());
            event.setEventDate(eventDTO.getEventDate());
            event.setDepartureTime(eventDTO.getDepartureTime());
            event.setReturnTime(eventDTO.getReturnTime());
            event.setNumberOfPassengers(eventDTO.getNumberOfPassengers());
            event.setEventValue(eventDTO.getEventValue());
            
            logger.info("Dados básicos do evento configurados");
            

            logger.info("Buscando funcionário com ID: {}", eventDTO.getEmployeeId());
            User employee = userService.findById(eventDTO.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado com o ID: " + eventDTO.getEmployeeId()));
            event.setEmployee(employee);
            logger.info("Funcionário associado: {}", employee.getId());
            

            logger.info("Buscando motorista com ID: {}", eventDTO.getDriverId());
            Driver driver = driverService.findById(eventDTO.getDriverId())
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado com o ID: " + eventDTO.getDriverId()));
            event.setDriver(driver);
            logger.info("Motorista associado: {}", driver.getId());
            

            logger.info("Buscando ônibus com ID: {}", eventDTO.getBusId());
            Bus bus = busService.findById(eventDTO.getBusId())
                .orElseThrow(() -> new IllegalArgumentException("Ônibus não encontrado com o ID: " + eventDTO.getBusId()));
            event.setBus(bus);
            logger.info("Ônibus associado: {}", bus.getId());
            

            logger.info("Salvando evento...");
            Event savedEvent = eventService.save(event);
            logger.info("Evento salvo com sucesso. ID: {}", savedEvent.getId());
            
            return ResponseEntity.ok(EventMapper.toDTO(savedEvent));
            
        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação ao criar evento: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Erro inesperado ao criar evento", e);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) {
        try {

            Event existingEvent = eventService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com o ID: " + id));
            

            existingEvent.setResponsibleName(eventDTO.getResponsibleName());
            existingEvent.setContactPhone(eventDTO.getContactPhone());
            existingEvent.setEventLocation(eventDTO.getEventLocation());
            existingEvent.setEventDate(eventDTO.getEventDate());
            existingEvent.setDepartureTime(eventDTO.getDepartureTime());
            existingEvent.setReturnTime(eventDTO.getReturnTime());
            existingEvent.setNumberOfPassengers(eventDTO.getNumberOfPassengers());
            existingEvent.setEventValue(eventDTO.getEventValue());
            

            if (!existingEvent.getEmployee().getId().equals(eventDTO.getEmployeeId())) {
                User employee = userService.findById(eventDTO.getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado com o ID: " + eventDTO.getEmployeeId()));
                existingEvent.setEmployee(employee);
            }
            

            Driver driver = driverService.findById(eventDTO.getDriverId())
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado com o ID: " + eventDTO.getDriverId()));
            existingEvent.setDriver(driver);
            

            Bus bus = busService.findById(eventDTO.getBusId())
                .orElseThrow(() -> new IllegalArgumentException("Ônibus não encontrado com o ID: " + eventDTO.getBusId()));
            existingEvent.setBus(bus);


            Event updatedEvent = eventService.save(existingEvent);
            return ResponseEntity.ok(EventMapper.toDTO(updatedEvent));
            
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao atualizar evento: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Erro inesperado ao atualizar evento", e);
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        return eventService.findById(id)
                .map(event -> {
                    eventService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
