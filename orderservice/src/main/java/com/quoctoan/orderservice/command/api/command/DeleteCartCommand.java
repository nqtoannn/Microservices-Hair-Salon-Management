package com.quoctoan.orderservice.command.api.command;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
public class DeleteCartCommand {
    @TargetAggregateIdentifier
    private String id;

    public DeleteCartCommand(String id) {
        super();
        this.id = id;
    }
}
