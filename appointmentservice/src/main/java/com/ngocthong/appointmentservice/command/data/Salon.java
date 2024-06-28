package com.ngocthong.appointmentservice.command.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Salon {
    @Id
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private Boolean isActive;
}
