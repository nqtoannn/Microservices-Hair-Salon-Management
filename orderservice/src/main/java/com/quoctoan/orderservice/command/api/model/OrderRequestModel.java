package com.quoctoan.orderservice.command.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Id;
@Getter
@Setter
    public class OrderRequestModel {
    private String customerId;
    private String payId;
    private Double totalPrice;
    private String[] listCartId;
}
