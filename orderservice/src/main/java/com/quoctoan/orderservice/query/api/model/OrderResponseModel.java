package com.quoctoan.orderservice.query.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderResponseModel {
    private String id;
    private LocalDate orderDate;
    private Double totalPrice;
    private String customerName;
    private String status;
    private String payId;
    private List<OrderItemResponseModel> orderItems;
}
