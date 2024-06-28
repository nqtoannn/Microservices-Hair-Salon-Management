package com.quoctoan.orderservice.command.api.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCartDeletedEvent {
    private String customerId;
}
