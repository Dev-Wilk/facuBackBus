package com.facu.backbus.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import com.facu.backbus.model.enums.BusStatus;


@Entity
@Table(name = "bus")
public class Bus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(max = 7, message = "A placa deve ter no máximo 7 caracteres")
    private String plate;

    @Column(nullable = false)
    @Min(value = 1, message = "A capacidade máxima deve ser maior que zero")
    private Integer maxCapacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusStatus status;
    
  
    // Constructors
    public Bus() {}

    public Bus(String plate, Integer maxCapacity, BusStatus status) {
        this.plate = plate;
        this.maxCapacity = maxCapacity;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getPlate() { 
        return plate; 
    }
    
    public void setPlate(String plate) { 
        this.plate = plate; 
    }

    public Integer getMaxCapacity() { 
        return maxCapacity; 
    }
    
    public void setMaxCapacity(Integer maxCapacity) { 
        this.maxCapacity = maxCapacity; 
    }

    public BusStatus getStatus() { 
        return status; 
    }
    
    public void setStatus(BusStatus status) { 
        this.status = status; 
    }
    
  
}
