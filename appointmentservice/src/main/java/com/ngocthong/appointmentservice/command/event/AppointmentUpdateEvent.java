package com.ngocthong.appointmentservice.command.event;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class AppointmentUpdateEvent {
        private String appointmentId;
        private LocalDate appointmentDate;
        private LocalTime appointmentTime;
        private String salonId;
        private String serviceId;
        private String userId;
}
