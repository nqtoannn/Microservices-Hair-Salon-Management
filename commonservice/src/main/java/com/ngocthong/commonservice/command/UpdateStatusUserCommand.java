package com.ngocthong.commonservice.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusUserCommand {
    @TargetAggregateIdentifier
    private String userId;
    private String status;
}
