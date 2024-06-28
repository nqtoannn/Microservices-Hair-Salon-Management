package com.ngocthong.productservice.command.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Product extends BaseEntity{
	@Id
	private String id;
	private String name;
	private String description;
	@Column(name = "image_url")
	private String imageUrl;
	private String categoryId;
}
