package com.quoctoan.orderservice.command.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
    public class BuyNowRequestModel {
    private String customerId;
    private String productItemId;
    private String payId;
    private Double totalPrice;

}
