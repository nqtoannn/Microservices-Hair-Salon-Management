package com.ngocthong.commonservice.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Getter
@Setter
@AllArgsConstructor
public class UpdateQuantityProductItemCommand {
    @TargetAggregateIdentifier
    private String id;
    private Integer quantity;
}
