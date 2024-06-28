package com.ngocthong.productservice.command.model;

import lombok.Data;

@Data
public class ProductItemRequestModel {
    private Double price;
    private String imageUrl;
    private String productItemName;
    private String description;
    private Integer quantity;
    private Integer warrantyTime;
}
