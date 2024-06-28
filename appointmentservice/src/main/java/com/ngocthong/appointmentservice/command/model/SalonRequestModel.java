package com.ngocthong.appointmentservice.command.model;

import lombok.Data;
@Data
public class SalonRequestModel {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private Boolean isActive;
}
