package com.facu.backbus.service;

import com.facu.backbus.model.Driver;
import com.facu.backbus.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {
    public List<Driver> findAllByStatus(com.facu.backbus.model.enums.DriverStatus status) {
        return driverRepository.findAllByStatus(status);
    }
    private final DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    public Optional<Driver> findById(Long id) {
        return driverRepository.findById(id);
    }

    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    public void deleteById(Long id) {
        driverRepository.deleteById(id);
    }
    
    /**
     * Busca todos os motoristas pelos seus IDs
     * @param ids Lista de IDs dos motoristas a serem buscados
     * @return Lista de motoristas encontrados
     */
    public List<Driver> findAllById(List<Long> ids) {
        return driverRepository.findAllById(ids);
    }
}
