package com.quoctoan.orderservice.query.api.queries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRevenueOrderBetween {
    private Integer startMonth;
    private Integer endMonth;
    private Integer year;
}
