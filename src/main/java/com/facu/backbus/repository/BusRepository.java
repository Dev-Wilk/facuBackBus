package com.facu.backbus.repository;

import java.util.List;

import com.facu.backbus.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findAllByStatus(com.facu.backbus.model.enums.BusStatus status);
}
