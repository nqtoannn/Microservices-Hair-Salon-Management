package com.quoctoan.orderservice.command.api.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDeleteEvent {
    private String id;
}
