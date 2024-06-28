package com.ngocthong.commonservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductItemResponseCommonModel {
    private String productItemId;
    private Double price;
    private String status;
    private String productId;
    private String imageUrl;
    private String productItemName;
    private String description;
    private Integer quantity;
}
