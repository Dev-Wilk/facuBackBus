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

@RestController
@RequestMapping("/buses")
@CrossOrigin(origins = "*")
public class BusController {

    private final BusService busService;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    public List<BusDTO> getAllBuses() {
        return busService.findAll().stream()
                .map(BusMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusDTO> getBusById(@PathVariable Long id) {
        return busService.findById(id)
                .map(BusMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BusDTO createBus(@RequestBody BusDTO busDTO) {
        Bus bus = BusMapper.toEntity(busDTO);
        return BusMapper.toDTO(busService.save(bus));
    }

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
