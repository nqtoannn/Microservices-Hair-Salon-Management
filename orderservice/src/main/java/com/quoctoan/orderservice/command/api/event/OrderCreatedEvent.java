package com.quoctoan.orderservice.command.api.event;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderCreatedEvent {
	private String id;
    private LocalDate orderDate;
    private Double totalPrice;
    private String customerId;
    private String orderStatus;
    private String payId;
    private String[] listCart;
}
