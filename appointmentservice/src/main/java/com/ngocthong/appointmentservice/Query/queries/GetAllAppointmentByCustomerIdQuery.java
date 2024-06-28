package com.ngocthong.appointmentservice.Query.queries;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetAllAppointmentByCustomerIdQuery {
    private String customerId;
}
