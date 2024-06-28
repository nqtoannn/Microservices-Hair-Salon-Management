package com.ngocthong.appointmentservice.command.command;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class CreateAppointmentCommand {
    @TargetAggregateIdentifier
    private String appointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private Double price;
    private String customerId;
    private String salonId;
    private String serviceId;
    private String userId;

    public CreateAppointmentCommand(String appointmentId, LocalDate appointmentDate, LocalTime appointmentTime, Double price, String customerId, String salonId, String serviceId, String userId) {
       super();
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.price = price;
        this.customerId = customerId;
        this.salonId = salonId;
        this.serviceId = serviceId;
        this.userId = userId;
    }
}
