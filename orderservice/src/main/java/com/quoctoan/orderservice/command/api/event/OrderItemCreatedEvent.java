package com.quoctoan.orderservice.command.api.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemCreatedEvent {
    private String id;
    private String orderId;
    private String productItemId;
    private Double price;
    private Integer quantity;

}
