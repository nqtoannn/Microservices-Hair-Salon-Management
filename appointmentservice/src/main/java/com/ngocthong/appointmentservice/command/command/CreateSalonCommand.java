package com.ngocthong.appointmentservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Setter
@Getter
@AllArgsConstructor
public class CreateSalonCommand {
    @TargetAggregateIdentifier
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private Boolean isActive;
}
