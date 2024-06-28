package com.quoctoan.orderservice.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderUpdateStatusRequestModel {
    private String Id;
    private String orderStatus;
}
