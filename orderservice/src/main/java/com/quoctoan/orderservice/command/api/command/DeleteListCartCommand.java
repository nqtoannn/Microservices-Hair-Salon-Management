package com.quoctoan.orderservice.command.api.command;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
public class DeleteListCartCommand {
    @TargetAggregateIdentifier
    private String[] listCartId;

    public DeleteListCartCommand(String[] listCartId) {
        super();
        this.listCartId = listCartId;
    }
}
