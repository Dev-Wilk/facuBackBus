package com.facu.backbus.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.facu.backbus.validation.ValidDateRange;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "eventt")
@ValidDateRange(startDate = "eventDepartureDate", endDate = "eventReturnDate", message = "A data de partida deve ser anterior à data de retorno")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String responsibleName;

    @Column(nullable = false)
    @Pattern(regexp = "^\\d{10,11}$", message = "O número de telefone deve conter entre 10 e 11 dígitos.")
    @Size(min = 10, max = 11, message = "O número de telefone deve ter entre 10 e 11 dígitos.")
    private String contactPhone;

    @Column(nullable = false)
    private String eventLocation;

    @Column(nullable = false)
    @NotNull(message = "A data de partida é obrigatória")
    private LocalDate eventDepartureDate;

    @Column(nullable = false)
    @NotNull(message = "O horário de partida é obrigatório")
    private LocalTime departureTime;

    @Column(nullable = false)
    @NotNull(message = "A data de retorno é obrigatória")
    private LocalDate eventReturnDate;

    @Column(nullable = false)
    @NotNull(message = "O horário de retorno é obrigatório")
    private LocalTime returnTime;

    @Column(nullable = false)
    private Integer numberOfPassengers;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @Column(nullable = false)
    private BigDecimal eventValue;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    // Constructors
    public Event() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public LocalDate getEventDepartureDate() {
        return eventDepartureDate;
    }

    public void setEventDepartureDate(LocalDate eventDepartureDate) {
        this.eventDepartureDate = eventDepartureDate;
    }

    public LocalDate getEventReturnDate() {
        return eventReturnDate;
    }

    public void setEventReturnDate(LocalDate eventReturnDate) {
        this.eventReturnDate = eventReturnDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalTime returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public BigDecimal getEventValue() {
        return eventValue;
    }

    public void setEventValue(BigDecimal eventValue) {
        this.eventValue = eventValue;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }
}