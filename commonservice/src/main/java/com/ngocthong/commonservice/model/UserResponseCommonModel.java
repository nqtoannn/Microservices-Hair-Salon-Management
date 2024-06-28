package com.ngocthong.commonservice.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseCommonModel {
    private String id;
    private String userName;
    private String fullName;
    private String status;
    private String email;
    private String phoneNumber;
    private String address;
}
