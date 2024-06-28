package com.quoctoan.orderservice.command.api.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    private String id;
    private String orderId;
    private String productItemId;
    private Integer quantity;
    private Double price;
}
