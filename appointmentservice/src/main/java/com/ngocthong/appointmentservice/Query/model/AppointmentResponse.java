package com.ngocthong.appointmentservice.Query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private String appointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    private Double price;
    private String customerName;
    private String salonName;
    private String serviceName;
    private String userName;
    private String salonAddress;
}
