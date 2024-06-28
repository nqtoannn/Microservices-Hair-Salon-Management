package com.ngocthong.userservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserProfileCommand {
    @TargetAggregateIdentifier
    private String id;
    private String userName;
    private String fullName;
    private String phoneNumber;
    private String address;

}
