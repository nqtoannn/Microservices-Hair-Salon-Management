package com.quoctoan.orderservice.command.api.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartBuyNowCreatedEvent {
    private String id;
    private String customerId;
    private String productItemId;
    private Integer quantity;
}
