package com.ngocthong.userservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserCommand {
    @TargetAggregateIdentifier
    private String userId;
    private String username;
    private String password;
    private String employeeId;
    private String role;
    private String status;
}
