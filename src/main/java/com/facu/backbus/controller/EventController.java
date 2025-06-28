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

    /**
     * Lista todos os eventos cadastrados.
     * @return Lista de DTOs de eventos
     */
    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.findAll().stream()
                .map(EventMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um evento pelo ID.
     * @param id ID do evento
     * @return DTO do evento encontrado ou 404 se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return eventService.findById(id)
                .map(EventMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo evento.
     * @param eventDTO DTO com os dados do evento a ser criado
     * @return DTO do evento criado
     * @throws IllegalArgumentException se o ID do funcionário não for fornecido
     */
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        try {
            logger.info("Iniciando criação de evento: {}", eventDTO);
            
            // Cria o evento com os dados básicos
            Event event = new Event();
            event.setResponsibleName(eventDTO.getResponsibleName());
            event.setContactPhone(eventDTO.getContactPhone());
            event.setEventLocation(eventDTO.getEventLocation());
            event.setEventDepartureDate(eventDTO.getEventDepartureDate());
            event.setEventReturnDate(eventDTO.getEventReturnDate());
            event.setDepartureTime(eventDTO.getDepartureTime());
            event.setReturnTime(eventDTO.getReturnTime());
            event.setNumberOfPassengers(eventDTO.getNumberOfPassengers());
            event.setEventValue(eventDTO.getEventValue());
            
            logger.info("Dados básicos do evento configurados");
            
            // Define o funcionário responsável
            logger.info("Buscando funcionário com ID: {}", eventDTO.getEmployeeId());
            User employee = userService.findById(eventDTO.getEmployeeId());
            if (employee == null) {
                throw new IllegalArgumentException("Funcionário não encontrado com o ID: " + eventDTO.getEmployeeId());
            }
            event.setEmployee(employee);
            logger.info("Funcionário associado: {}", employee.getId());
            
            // Busca e associa o motorista
            logger.info("Buscando motorista com ID: {}", eventDTO.getDriverId());
            Driver driver = driverService.findById(eventDTO.getDriverId())
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado com o ID: " + eventDTO.getDriverId()));
            if (driver.getStatus() != com.facu.backbus.model.enums.DriverStatus.AVAILABLE) {
                logger.error("Motorista com ID {} não está disponível.", driver.getId());
                return ResponseEntity.badRequest().body(null);
            }
            event.setDriver(driver);
            logger.info("Motorista associado: {}", driver.getId());
            
            // Busca e associa o ônibus
            logger.info("Buscando ônibus com ID: {}", eventDTO.getBusId());
            Bus bus = busService.findById(eventDTO.getBusId())
                .orElseThrow(() -> new IllegalArgumentException("Ônibus não encontrado com o ID: " + eventDTO.getBusId()));
            if (bus.getStatus() != com.facu.backbus.model.enums.BusStatus.AVAILABLE) {
                logger.error("Ônibus com ID {} não está disponível.", bus.getId());
                return ResponseEntity.badRequest().body(null);
            }
            event.setBus(bus);
            logger.info("Ônibus associado: {}", bus.getId());
            
            // Salva o evento
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

    /**
     * Atualiza um evento existente.
     * @param id ID do evento a ser atualizado
     * @param eventDTO DTO com os novos dados do evento
     * @return DTO do evento atualizado ou 404 se não encontrado
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) {
        try {
            eventDTO.setId(id);
            
            // Busca o evento existente
            Event existingEvent = eventService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com o ID: " + id));
            
            // Atualiza os campos básicos
            existingEvent.setResponsibleName(eventDTO.getResponsibleName());
            existingEvent.setContactPhone(eventDTO.getContactPhone());
            existingEvent.setEventLocation(eventDTO.getEventLocation());
            existingEvent.setEventDepartureDate(eventDTO.getEventDepartureDate());
            existingEvent.setEventReturnDate(eventDTO.getEventReturnDate());
            existingEvent.setDepartureTime(eventDTO.getDepartureTime());
            existingEvent.setReturnTime(eventDTO.getReturnTime());
            existingEvent.setNumberOfPassengers(eventDTO.getNumberOfPassengers());
            existingEvent.setEventValue(eventDTO.getEventValue());
            
            // Atualiza o funcionário responsável, se necessário
            if (!existingEvent.getEmployee().getId().equals(eventDTO.getEmployeeId())) {
                User newEmployee = userService.findById(eventDTO.getEmployeeId());
                if (newEmployee == null) {
                    throw new IllegalArgumentException("Funcionário não encontrado com o ID: " + eventDTO.getEmployeeId());
                }
                existingEvent.setEmployee(newEmployee);
            }
            
            // Busca e associa o novo motorista
            Driver driver = driverService.findById(eventDTO.getDriverId())
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado com o ID: " + eventDTO.getDriverId()));
            if (driver.getStatus() != com.facu.backbus.model.enums.DriverStatus.AVAILABLE) {
                logger.error("Motorista com ID {} não está disponível.", driver.getId());
                return ResponseEntity.badRequest().build();
            }
            existingEvent.setDriver(driver);
            
            // Busca e associa o novo ônibus
            Bus bus = busService.findById(eventDTO.getBusId())
                .orElseThrow(() -> new IllegalArgumentException("Ônibus não encontrado com o ID: " + eventDTO.getBusId()));
            if (bus.getStatus() != com.facu.backbus.model.enums.BusStatus.AVAILABLE) {
                logger.error("Ônibus com ID {} não está disponível.", bus.getId());
                return ResponseEntity.badRequest().build();
            }
            existingEvent.setBus(bus);
            
            // Salva as alterações
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

    /**
     * Remove um evento pelo ID.
     * @param id ID do evento a ser removido
     * @return Resposta vazia com status 204 (No Content) ou 404 se não encontrado
     */
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
