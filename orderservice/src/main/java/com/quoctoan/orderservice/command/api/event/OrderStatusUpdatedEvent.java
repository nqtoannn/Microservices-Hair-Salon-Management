package com.quoctoan.orderservice.command.api.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusUpdatedEvent {
    private String id;
    private String orderStatus;

}
