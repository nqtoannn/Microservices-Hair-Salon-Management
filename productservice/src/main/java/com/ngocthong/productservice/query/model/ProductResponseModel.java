package com.ngocthong.productservice.query.model;

import lombok.Data;

@Data
public class ProductResponseModel {
	private String id;
	private String name;
	private String imageUrl;
	private String description;
	private String categoryId;
}
