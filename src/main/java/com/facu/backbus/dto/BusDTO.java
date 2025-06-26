package com.facu.backbus.dto;

import com.facu.backbus.model.enums.BusStatus;

public class BusDTO {
    private Long id;
    private String plate;
    private Integer maxCapacity;
    private BusStatus status;

    public BusDTO() {}

    public BusDTO(Long id, String plate, Integer maxCapacity, BusStatus status) {
        this.id = id;
        this.plate = plate;
        this.maxCapacity = maxCapacity;
        this.status = status;
    }

    public BusDTO(com.facu.backbus.model.Bus bus) {
        this.id = bus.getId();
        this.plate = bus.getPlate();
        this.maxCapacity = bus.getMaxCapacity();
        this.status = bus.getStatus();
    }

    public com.facu.backbus.model.Bus toEntity() {
        com.facu.backbus.model.Bus bus = new com.facu.backbus.model.Bus();
        bus.setId(this.id);
        bus.setPlate(this.plate);
        bus.setMaxCapacity(this.maxCapacity);
        bus.setStatus(this.status);
        return bus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }
    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }
    public BusStatus getStatus() { return status; }
    public void setStatus(BusStatus status) { this.status = status; }
}
