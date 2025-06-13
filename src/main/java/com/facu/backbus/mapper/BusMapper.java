package com.facu.backbus.mapper;

import com.facu.backbus.dto.BusDTO;
import com.facu.backbus.model.Bus;

public class BusMapper {
    public static BusDTO toDTO(Bus bus) {
        return new BusDTO(bus);
    }
    public static Bus toEntity(BusDTO dto) {
        return dto.toEntity();
    }
} 