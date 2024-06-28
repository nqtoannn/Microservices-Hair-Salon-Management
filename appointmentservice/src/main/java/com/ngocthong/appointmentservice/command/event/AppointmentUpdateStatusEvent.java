package com.ngocthong.appointmentservice.command.event;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AppointmentUpdateStatusEvent {
    private String appointmentId;
    private String status;
}
