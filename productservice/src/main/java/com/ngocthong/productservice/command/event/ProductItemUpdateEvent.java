package com.ngocthong.productservice.command.event;

import lombok.Data;

@Data
public class ProductItemUpdateEvent {
    private String productItemId;
    private Double price;
    private Integer quantity;
    private String status;
    private String imageUrl;
    private String productItemName;
    private String description;
    private Integer warrantyTime;
}
