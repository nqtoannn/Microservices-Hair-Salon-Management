package com.quoctoan.orderservice.command.api.command;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Getter
@Setter
@AllArgsConstructor
public class UpdateOrderCommand {
    @TargetAggregateIdentifier
    private String id;
    private Double totalPrice;
    private String customerId;
    private String orderStatus;
    private String payId;
}
