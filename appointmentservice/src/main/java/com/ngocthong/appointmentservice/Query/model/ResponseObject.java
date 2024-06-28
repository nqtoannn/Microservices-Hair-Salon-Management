package com.ngocthong.appointmentservice.Query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseObject {
    private String status;
    private String message;
    private  Object data;
}