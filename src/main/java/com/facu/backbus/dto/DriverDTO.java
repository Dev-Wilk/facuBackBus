package com.facu.backbus.dto;

import com.facu.backbus.model.enums.DriverStatus;

public class DriverDTO {
    private Long id;
    private String fullName;
    private String identificationNumber;
    private String contact;
    private DriverStatus status;

    public DriverDTO() {}

    public DriverDTO(Long id, String fullName, String identificationNumber, String contact, DriverStatus status) {
        this.id = id;
        this.fullName = fullName;
        this.identificationNumber = identificationNumber;
        this.contact = contact;
        this.status = status;
    }

    public DriverDTO(com.facu.backbus.model.Driver driver) {
        this.id = driver.getId();
        this.fullName = driver.getFullName();
        this.identificationNumber = driver.getIdentificationNumber();
        this.contact = driver.getContact();
        this.status = driver.getStatus();
    }

    public com.facu.backbus.model.Driver toEntity() {
        com.facu.backbus.model.Driver driver = new com.facu.backbus.model.Driver();
        driver.setId(this.id);
        driver.setFullName(this.fullName);
        driver.setIdentificationNumber(this.identificationNumber);
        driver.setContact(this.contact);
        driver.setStatus(this.status);
        return driver;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getIdentificationNumber() { return identificationNumber; }
    public void setIdentificationNumber(String identificationNumber) { this.identificationNumber = identificationNumber; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public DriverStatus getStatus() { return status; }
    public void setStatus(DriverStatus status) { this.status = status; }
}
