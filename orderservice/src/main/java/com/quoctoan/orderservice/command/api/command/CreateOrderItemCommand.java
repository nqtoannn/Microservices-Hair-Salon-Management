package com.quoctoan.orderservice.command.api.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
public class CreateOrderItemCommand {
    @TargetAggregateIdentifier
    private String id;
    private String orderId;
    private String productItemId;
    private Double price;
    private Integer quantity;
}
