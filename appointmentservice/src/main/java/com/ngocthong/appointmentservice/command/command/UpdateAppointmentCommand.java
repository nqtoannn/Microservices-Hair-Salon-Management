package com.ngocthong.appointmentservice.command.command;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class UpdateAppointmentCommand {
    @TargetAggregateIdentifier
    private String appointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private Integer salonId;
    private Integer serviceId;
    private Integer userId;

    public UpdateAppointmentCommand(String appointmentId, LocalDate appointmentDate, LocalTime appointmentTime, Integer salonId, Integer serviceId, Integer userId) {
        super();
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.salonId = salonId;
        this.serviceId = serviceId;
        this.userId = userId;
    }
}
