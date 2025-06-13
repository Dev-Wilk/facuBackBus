package com.facu.backbus.mapper;

import com.facu.backbus.dto.DriverDTO;
import com.facu.backbus.model.Driver;

public class DriverMapper {
    public static DriverDTO toDTO(Driver driver) {
        return new DriverDTO(driver);
    }
    public static Driver toEntity(DriverDTO dto) {
        return dto.toEntity();
    }
} 