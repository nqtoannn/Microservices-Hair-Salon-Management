package com.ngocthong.commonservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SalonResponseCommonModel {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private Boolean isActive;
}
