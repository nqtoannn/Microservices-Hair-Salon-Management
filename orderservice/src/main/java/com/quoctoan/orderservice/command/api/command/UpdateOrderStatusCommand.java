package com.quoctoan.orderservice.command.api.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
public class UpdateOrderStatusCommand {
    @TargetAggregateIdentifier
        private String Id;
        private String orderStatus;
}
