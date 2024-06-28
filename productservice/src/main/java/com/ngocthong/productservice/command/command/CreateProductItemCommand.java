package com.ngocthong.productservice.command.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductItemCommand {
    @TargetAggregateIdentifier
    private String productItemId;
    private Double price;
    private String imageUrl;
    private String productItemName;
    private String description;
    private Integer quantity;
    private Integer warrantyTime;

}
