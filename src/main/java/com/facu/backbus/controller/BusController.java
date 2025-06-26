package com.facu.backbus.controller;

import com.facu.backbus.dto.BusDTO;
import com.facu.backbus.mapper.BusMapper;
import com.facu.backbus.model.Bus;
import com.facu.backbus.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador responsável por gerenciar operações relacionadas a ônibus.
 * Permite listar, buscar por ID, criar, atualizar e deletar ônibus.
 */
import com.facu.backbus.model.enums.BusStatus;
@RestController
@RequestMapping("/buses")
@CrossOrigin(origins = "*")
public class BusController {

    private final BusService busService;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    /**
     * Lista todos os ônibus cadastrados.
     * @return Lista de DTOs de ônibus
     */
    @GetMapping
    public List<BusDTO> getAllBuses() {
        return busService.findAll().stream()
                .map(BusMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/disponiveis")
    public List<BusDTO> getAvailableBuses() {
        return busService.findAllByStatus(BusStatus.AVAILABLE).stream()
                .map(BusMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um ônibus pelo ID.
     * @param id ID do ônibus
     * @return DTO do ônibus encontrado ou 404 se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<BusDTO> getBusById(@PathVariable Long id) {
        return busService.findById(id)
                .map(BusMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo ônibus.
     * @param busDTO DTO com os dados do ônibus a ser criado
     * @return DTO do ônibus criado
     */
    @PostMapping
    public BusDTO createBus(@RequestBody BusDTO busDTO) {
        Bus bus = BusMapper.toEntity(busDTO);
        return BusMapper.toDTO(busService.save(bus));
    }

    /**
     * Atualiza um ônibus existente.
     * @param id ID do ônibus a ser atualizado
     * @param busDTO DTO com os novos dados do ônibus
     * @return DTO do ônibus atualizado ou 404 se não encontrado
     */
    @PutMapping("/{id}")
    public ResponseEntity<BusDTO> updateBus(@PathVariable Long id, @RequestBody BusDTO busDTO) {
        return busService.findById(id)
                .map(existingBus -> {
                    busDTO.setId(id);
                    Bus updated = busService.save(BusMapper.toEntity(busDTO));
                    return ResponseEntity.ok(BusMapper.toDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um ônibus pelo ID.
     * @param id ID do ônibus a ser removido
     * @return Resposta vazia com status 204 (No Content) ou 404 se não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        return busService.findById(id)
                .map(bus -> {
                    busService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
