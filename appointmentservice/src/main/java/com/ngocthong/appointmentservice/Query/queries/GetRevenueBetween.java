package com.ngocthong.appointmentservice.Query.queries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRevenueBetween {
    private Integer startMonth;
    private Integer endMonth;
    private Integer year;
}
