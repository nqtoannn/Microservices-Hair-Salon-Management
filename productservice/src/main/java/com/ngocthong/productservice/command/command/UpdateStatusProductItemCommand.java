package com.ngocthong.productservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusProductItemCommand {
    @TargetAggregateIdentifier
    private String id;
    private String status;
}
