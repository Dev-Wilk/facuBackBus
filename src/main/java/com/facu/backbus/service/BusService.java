package com.facu.backbus.service;

import com.facu.backbus.model.Bus;
import com.facu.backbus.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class BusService {
    public List<Bus> findAllByStatus(com.facu.backbus.model.enums.BusStatus status) {
        return busRepository.findAllByStatus(status);
    }
    private final BusRepository busRepository;

    @Autowired
    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    public Optional<Bus> findById(Long id) {
        return busRepository.findById(id);
    }

    public Bus save(Bus bus) {
        return busRepository.save(bus);
    }

    public void deleteById(Long id) {
        busRepository.deleteById(id);
    }
    
    /**
     * Busca todos os ônibus pelos seus IDs
     * @param ids Lista de IDs dos ônibus a serem buscados
     * @return Lista de ônibus encontrados
     */
    public List<Bus> findAllById(List<Long> ids) {
        return busRepository.findAllById(ids);
    }
}
