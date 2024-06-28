package com.ngocthong.appointmentservice.command.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequestModel {
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private Double price;
    private String customerId;
    private String salonId;
    private String serviceId;
    private String userId;
}
