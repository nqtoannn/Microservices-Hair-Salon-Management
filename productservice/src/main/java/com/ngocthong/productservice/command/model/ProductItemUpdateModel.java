package com.ngocthong.productservice.command.model;

import lombok.Data;

@Data
public class ProductItemUpdateModel {
    private String productItemId;
    private Double price;
    private Integer quantity;
    private String status;
    private String imageUrl;
    private String productItemName;
    private String description;
    private Integer warrantyTime;
}
