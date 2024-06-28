package com.ngocthong.productservice.command.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class CreateProductCommand{
    @TargetAggregateIdentifier
    private String id;
    private String imageUrl;
    private String name;
    private String description;
    private String categoryId;

    public CreateProductCommand(String id, String name, String description, String imageUrl, String categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }
}
