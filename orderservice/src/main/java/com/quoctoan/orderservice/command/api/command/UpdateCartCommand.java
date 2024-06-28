package com.quoctoan.orderservice.command.api.command;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Getter
@Setter
public class UpdateCartCommand {
    @TargetAggregateIdentifier
    private String id;
    private String customerId;
    private String productItemId;
    private Integer quantity;

    public UpdateCartCommand(String id, String customerId, String productItemId, Integer quantity) {
        this.id = id;
        this.customerId = customerId;
        this.productItemId = productItemId;
        this.quantity = quantity;
    }
}
