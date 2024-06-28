package com.quoctoan.orderservice.query.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseModel {
    private String id;
    private String orderId;
    private String productItemId;
    private String productItemName;
    private String imageUrl;
    private String description;
    private Integer quantity;
    private Double price;
}
