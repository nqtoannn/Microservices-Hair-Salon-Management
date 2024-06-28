package com.ngocthong.productservice.command.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product_item")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductItem extends BaseEntity {
    @Id
    private String productItemId;
    private Double price;
    private String status;
    private String imageUrl;
    private String productItemName;
    private String description;
    private Integer quantity;
    private Integer warrantyTime;
}
