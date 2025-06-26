package com.facu.backbus.repository;

import java.util.List;

import com.facu.backbus.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findAllByStatus(com.facu.backbus.model.enums.DriverStatus status);
}
