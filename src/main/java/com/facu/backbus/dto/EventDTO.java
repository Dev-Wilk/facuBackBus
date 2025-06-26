package com.facu.backbus.dto;

import com.facu.backbus.mapper.UserMapper;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import com.facu.backbus.validation.ValidDateRange;
import com.facu.backbus.validation.BusScheduleFree;
import com.facu.backbus.validation.DriverScheduleFree;

@ValidDateRange(startDate = "eventDepartureDate", endDate = "eventReturnDate", message = "A data de partida deve ser anterior ou igual à data de retorno")
@DriverScheduleFree
@BusScheduleFree
public class EventDTO {
    private Long id;

    @NotBlank(message = "O nome do responsável é obrigatório")
    private String responsibleName;

    @NotBlank(message = "O telefone de contato é obrigatório")
    private String contactPhone;

    @NotBlank(message = "A localização do evento é obrigatória")
    private String eventLocation;

    @NotNull(message = "A data de partida é obrigatória")
    private LocalDate eventDepartureDate;

    @NotNull(message = "A data de retorno é obrigatória")
    private LocalDate eventReturnDate;

    @NotNull(message = "O horário de saída é obrigatório")
    private LocalTime departureTime;

    @NotNull(message = "O horário de retorno é obrigatório")
    private LocalTime returnTime;

    @NotNull(message = "O número de passageiros é obrigatório")
    @Min(value = 1, message = "Deve haver pelo menos 1 passageiro")
    private Integer numberOfPassengers;

    @NotNull(message = "O ID do funcionário é obrigatório")
    private Long employeeId;

    @NotNull(message = "O valor do evento é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que zero")
    private BigDecimal eventValue;

    @NotNull(message = "O ID do motorista é obrigatório")
    private Long driverId;

    @NotNull(message = "O ID do ônibus é obrigatório")
    private Long busId;

    // Novo campo para funcionário completo
    private UserDTO employee;

    public EventDTO() {
    }

    // Construtor que mapeia do model para o DTO
    public EventDTO(com.facu.backbus.model.Event event) {
        this.id = event.getId();
        this.responsibleName = event.getResponsibleName();
        this.contactPhone = event.getContactPhone();
        this.eventLocation = event.getEventLocation();
        this.eventDepartureDate = event.getEventDepartureDate();
        this.eventReturnDate = event.getEventReturnDate();
        this.departureTime = event.getDepartureTime();
        this.returnTime = event.getReturnTime();
        this.numberOfPassengers = event.getNumberOfPassengers();
        this.employeeId = event.getEmployee() != null ? event.getEmployee().getId() : null;
        this.eventValue = event.getEventValue();
        this.driverId = event.getDriver() != null ? event.getDriver().getId() : null;
        this.busId = event.getBus() != null ? event.getBus().getId() : null;

        this.employee = UserMapper.toDTO(event.getEmployee());
    }

    // Getters e setters

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

    public BigDecimal getEventValue() {
        return eventValue;
    }

    public void setEventValue(BigDecimal eventValue) {
        this.eventValue = eventValue;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public UserDTO getEmployee() {
        return employee;
    }

    public void setEmployee(UserDTO employee) {
        this.employee = employee;
    }
}