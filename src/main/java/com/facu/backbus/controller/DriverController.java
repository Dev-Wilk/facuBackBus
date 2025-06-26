package com.facu.backbus.controller;

import com.facu.backbus.dto.DriverDTO;
import com.facu.backbus.mapper.DriverMapper;
import com.facu.backbus.model.Driver;
import com.facu.backbus.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador responsável por gerenciar operações relacionadas a motoristas.
 * Permite listar, buscar por ID, criar, atualizar e deletar motoristas.
 */
import com.facu.backbus.model.enums.DriverStatus;
@RestController
@RequestMapping("/drivers")
@CrossOrigin(origins = "*")
public class DriverController {
    
    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    /**
     * Lista todos os motoristas cadastrados.
     * @return Lista de DTOs de motoristas
     */
    @GetMapping
    public List<DriverDTO> getAllDrivers() {
        return driverService.findAll().stream()
                .map(DriverMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/disponiveis")
    public List<DriverDTO> getAvailableDrivers() {
        return driverService.findAllByStatus(DriverStatus.AVAILABLE).stream()
                .map(DriverMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um motorista pelo ID.
     * @param id ID do motorista
     * @return DTO do motorista encontrado ou 404 se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getDriverById(@PathVariable Long id) {
        return driverService.findById(id)
                .map(DriverMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo motorista.
     * @param driverDTO DTO com os dados do motorista a ser criado
     * @return DTO do motorista criado
     */
    @PostMapping
    public DriverDTO createDriver(@RequestBody DriverDTO driverDTO) {
        Driver driver = DriverMapper.toEntity(driverDTO);
        return DriverMapper.toDTO(driverService.save(driver));
    }

    /**
     * Atualiza um motorista existente.
     * @param id ID do motorista a ser atualizado
     * @param driverDTO DTO com os novos dados do motorista
     * @return DTO do motorista atualizado ou 404 se não encontrado
     */
    @PutMapping("/{id}")
    public ResponseEntity<DriverDTO> updateDriver(@PathVariable Long id, @RequestBody DriverDTO driverDTO) {
        return driverService.findById(id)
                .map(existingDriver -> {
                    driverDTO.setId(id);
                    Driver updated = driverService.save(DriverMapper.toEntity(driverDTO));
                    return ResponseEntity.ok(DriverMapper.toDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um motorista pelo ID.
     * @param id ID do motorista a ser removido
     * @return Resposta vazia com status 204 (No Content) ou 404 se não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        return driverService.findById(id)
                .map(driver -> {
                    driverService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
