package com.quoctoan.hairservice.command.command;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
public class UpdateServiceCommand {
	@TargetAggregateIdentifier
	private String id;
    private String serviceName;
    private Double price;
    private String description;
    private String url;
	private String status;
	
    public UpdateServiceCommand(String id, String serviceName, Double price, String description, String url,String status) {
		super();
		this.id = id;
		this.serviceName = serviceName;
		this.price = price;
		this.description = description;
		this.url = url;
		this.status = status;
	}

}
