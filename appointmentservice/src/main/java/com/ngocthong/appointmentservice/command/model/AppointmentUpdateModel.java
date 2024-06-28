package com.ngocthong.appointmentservice.command.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class AppointmentUpdateModel {
    private String appointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private Integer salonId;
    private Integer serviceId;
    private Integer userId;
}
