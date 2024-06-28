package com.ngocthong.productservice.command.event;

import lombok.Data;

@Data
public class ProductCreatedEvent {
	private String id;
	private String name;
	private String description;
	private String imageUrl;
	private String categoryId;
}
