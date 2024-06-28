package com.ngocthong.productservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemResponseModel {
    private String productItemId;
    private Double price;
    private String status;
    private String imageUrl;
    private String productItemName;
    private String description;
    private Integer quantity;
    private Integer warrantyTime;
}
