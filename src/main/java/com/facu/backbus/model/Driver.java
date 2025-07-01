package com.facu.backbus.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import com.facu.backbus.model.enums.DriverStatus;


@Entity
@Table(name = "driver")
public class Driver {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String identificationNumber;

    @Column(nullable = false)
    @Pattern(regexp = "^\\d{10,11}$", message = "O número de contato deve conter entre 10 e 11 dígitos.")
    @Size(min = 10, max = 11, message = "O número de contato deve ter entre 10 e 11 dígitos.")
    private String contact;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverStatus status;

    // Constructors
    public Driver() {}

    public Driver(String fullName, String identificationNumber, String contact, DriverStatus status) {
        this.fullName = fullName;
        this.identificationNumber = identificationNumber;
        this.contact = contact;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getIdentificationNumber() { return identificationNumber; }
    public void setIdentificationNumber(String identificationNumber) { this.identificationNumber = identificationNumber; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    
    // Relacionamento bidirecional opcional - descomente se necessário
    // @OneToMany(mappedBy = "driver")
    // private List<Event> events = new ArrayList<>();

    public DriverStatus getStatus() { return status; }
    public void setStatus(DriverStatus status) { this.status = status; }
}


