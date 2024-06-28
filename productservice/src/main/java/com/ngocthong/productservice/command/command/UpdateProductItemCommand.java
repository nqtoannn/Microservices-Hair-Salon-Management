package com.ngocthong.productservice.command.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductItemCommand {
    @TargetAggregateIdentifier
    private String productItemId;
    private Double price;
    private Integer quantity;
    private String status;
    private String imageUrl;
    private String productItemName;
    private String description;
}
