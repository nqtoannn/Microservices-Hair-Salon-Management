package com.quoctoan.orderservice.command.api.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

	@Id
	private String Id;
    private LocalDate orderDate;
    private Double totalPrice;
    private String customerId;
    private String orderStatus;
    private String payId;

}
