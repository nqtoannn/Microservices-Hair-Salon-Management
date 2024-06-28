package com.ngocthong.productservice.command.model;

import lombok.Data;

@Data
public class ProductUpdateModel {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String categoryId;
}
