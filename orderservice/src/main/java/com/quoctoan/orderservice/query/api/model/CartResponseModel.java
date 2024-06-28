package com.quoctoan.orderservice.query.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseModel {
    private String id;
    private String customerId;
    private String productItemId;
    private String productItemName;
    private String imageUrl;
    private Integer quantity;
    private Double price;
}
