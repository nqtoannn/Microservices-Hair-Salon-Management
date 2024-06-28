package com.ngocthong.appointmentservice.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppointmentUpdateStatusModel {
    private String appointmentId;
    private String status;
}
