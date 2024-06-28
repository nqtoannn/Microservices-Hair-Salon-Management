package com.ngocthong.userservice.command.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserEvent {
    private String userId;
    private String username;
    private String password;
    private String employeeId;
    private String role;
    private String status;
}
