package com.ngocthong.productservice.command.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductItemQuantityUpdatedEvent {
    private String id;
    private Integer quantity;
}