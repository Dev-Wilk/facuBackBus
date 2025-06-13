package com.facu.backbus.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class EventDTO {
    private Long id;

    @NotBlank(message = "O nome do responsável é obrigatório")
    private String responsibleName;

    @NotBlank(message = "O telefone de contato é obrigatório")
    private String contactPhone;
 
    @NotBlank(message = "A localização do evento é obrigatória")
    private String eventLocation;

    @NotNull(message = "A data do evento é obrigatória")
    private LocalDate eventDate;
  
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

    public EventDTO() {}

    public EventDTO(com.facu.backbus.model.Eventt event) {
        this.id = event.getId();
        this.responsibleName = event.getResponsibleName();
        this.contactPhone = event.getContactPhone();
        this.eventLocation = event.getEventLocation();
        this.eventDate = event.getEventDate();
        this.departureTime = event.getDepartureTime();
        this.returnTime = event.getReturnTime();
        this.numberOfPassengers = event.getNumberOfPassengers();
        this.employeeId = event.getEmployee() != null ? event.getEmployee().getId() : null;
        this.eventValue = event.getEventValue();
        this.driverId = event.getDriver() != null ? event.getDriver().getId() : null;
        this.busId = event.getBus() != null ? event.getBus().getId() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getResponsibleName() { return responsibleName; }
    public void setResponsibleName(String responsibleName) { this.responsibleName = responsibleName; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getEventLocation() { return eventLocation; }
    public void setEventLocation(String eventLocation) { this.eventLocation = eventLocation; }
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public LocalTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalTime departureTime) { this.departureTime = departureTime; }
    public LocalTime getReturnTime() { return returnTime; }
    public void setReturnTime(LocalTime returnTime) { this.returnTime = returnTime; }
    public Integer getNumberOfPassengers() { return numberOfPassengers; }
    public void setNumberOfPassengers(Integer numberOfPassengers) { this.numberOfPassengers = numberOfPassengers; }
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public BigDecimal getEventValue() { return eventValue; }
    public void setEventValue(BigDecimal eventValue) { this.eventValue = eventValue; }
    
    public com.facu.backbus.model.Eventt toEntity() {
        com.facu.backbus.model.Eventt event = new com.facu.backbus.model.Eventt();
        event.setId(this.id);
        event.setResponsibleName(this.responsibleName);
        event.setContactPhone(this.contactPhone);
        event.setEventLocation(this.eventLocation);
        event.setEventDate(this.eventDate);
        event.setDepartureTime(this.departureTime);
        event.setReturnTime(this.returnTime);
        event.setNumberOfPassengers(this.numberOfPassengers);
        // Note: employee, driver e bus são configurados no serviço
        event.setEventValue(this.eventValue);
        // driver e bus serão configurados no serviço
        return event;
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
}