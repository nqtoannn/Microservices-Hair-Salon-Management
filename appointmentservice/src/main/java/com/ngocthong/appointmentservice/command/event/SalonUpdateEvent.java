package com.ngocthong.appointmentservice.command.event;

import lombok.Data;

@Data
public class SalonUpdateEvent {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private Boolean isActive;
}

