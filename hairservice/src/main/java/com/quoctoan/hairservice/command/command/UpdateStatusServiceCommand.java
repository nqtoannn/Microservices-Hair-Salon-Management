package com.quoctoan.hairservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusServiceCommand {
    @TargetAggregateIdentifier
    private String id;
    private String status;
}
