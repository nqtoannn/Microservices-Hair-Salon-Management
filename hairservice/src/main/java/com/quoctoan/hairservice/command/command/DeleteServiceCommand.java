package com.quoctoan.hairservice.command.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class DeleteServiceCommand {
	@TargetAggregateIdentifier
	private Integer id;
	
    public DeleteServiceCommand(Integer id) {
		super();
		this.id = id;
	}
}
