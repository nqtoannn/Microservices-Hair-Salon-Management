package com.ngocthong.appointmentservice.command.command;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Getter
@Setter
public class UpdateStatusAppointmentCommand {
    @TargetAggregateIdentifier
    private String appointmentId;
    private String status;

    public UpdateStatusAppointmentCommand(String appointmentId, String status) {
        super();
        this.appointmentId = appointmentId;
        this.status = status;
    }

}
