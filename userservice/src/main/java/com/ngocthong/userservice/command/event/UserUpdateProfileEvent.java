package com.ngocthong.userservice.command.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateProfileEvent {
    private String id;
    private String userName;
    private String fullName;
    private String phoneNumber;
    private String address;

}
