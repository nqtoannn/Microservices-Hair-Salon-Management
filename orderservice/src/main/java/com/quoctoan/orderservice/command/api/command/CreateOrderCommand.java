package com.quoctoan.orderservice.command.api.command;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
public class CreateOrderCommand {

	@TargetAggregateIdentifier
	private String id;
    private LocalDate orderDate;
    private Double totalPrice;
    private String customerId;
    private String orderStatus;
    private String payId;
	private String[] listCart;
    
	public CreateOrderCommand(String id, LocalDate orderDate, Double totalPrice, String customerId,
							  String orderStatus, String payId, String[] listCart) {
		super();
		this.id = id;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.customerId = customerId;
		this.orderStatus = orderStatus;
		this.payId = payId;
		this.listCart = listCart;
	}
		
}