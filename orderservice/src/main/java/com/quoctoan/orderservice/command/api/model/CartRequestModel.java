package com.quoctoan.orderservice.command.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestModel {
    private String id;
    private String customerId;
    private String productItemId;
    private Integer quantity;
}
