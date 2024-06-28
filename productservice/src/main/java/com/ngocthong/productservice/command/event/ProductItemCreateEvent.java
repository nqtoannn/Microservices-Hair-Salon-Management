package com.ngocthong.productservice.command.event;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductItemCreateEvent {
    private String productItemId;
    private Double price;
    private String imageUrl;
    private String productItemName;
    private String description;
    private Integer warrantyTime;
    private Integer quantity;
}
