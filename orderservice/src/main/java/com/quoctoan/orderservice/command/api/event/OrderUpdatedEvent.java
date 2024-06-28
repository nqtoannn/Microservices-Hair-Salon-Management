package com.quoctoan.orderservice.command.api.event;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderUpdatedEvent {
    private String id;
    private Double totalPrice;
    private String customerId;
    private String orderStatus;
    private String payId;
}
