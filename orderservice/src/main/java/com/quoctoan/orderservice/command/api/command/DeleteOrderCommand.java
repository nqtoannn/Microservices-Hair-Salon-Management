package com.quoctoan.orderservice.command.api.command;

import java.time.LocalDate;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class DeleteOrderCommand {
	@TargetAggregateIdentifier
	private String id;
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
