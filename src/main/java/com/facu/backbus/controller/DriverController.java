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

@RestController
@RequestMapping("/drivers")
@CrossOrigin(origins = "*")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public List<DriverDTO> getAllDrivers() {
        return driverService.findAll().stream()
                .map(DriverMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getDriverById(@PathVariable Long id) {
        return driverService.findById(id)
                .map(DriverMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DriverDTO createDriver(@RequestBody DriverDTO driverDTO) {
        Driver driver = DriverMapper.toEntity(driverDTO);
        return DriverMapper.toDTO(driverService.save(driver));
    }

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
