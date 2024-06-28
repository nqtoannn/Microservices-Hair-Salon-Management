package com.ngocthong.productservice.command.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class UpdateProductCommand {
    @TargetAggregateIdentifier
    private String id;
    private String imageUrl;
    private String name;
    private String description;
    private String categoryId;

    public UpdateProductCommand(String id, String name, String description, String imageUrl, String categoryId) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

}
