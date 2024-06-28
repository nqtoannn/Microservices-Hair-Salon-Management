package com.ngocthong.appointmentservice.command.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointment")
public class Appointment {
    @Id
    private String appointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
    private String customerId;
    private Double price;
    private String salonId;
    private String serviceId;
    private String userId;
}
