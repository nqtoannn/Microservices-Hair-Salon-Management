package com.quoctoan.hairservice.command.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateServiceCommand {
	@TargetAggregateIdentifier
	private String id;
    private String serviceName;
    private Double price;
    private String description;
    private String url;
	private String status;
    public CreateServiceCommand(String id, String serviceName, Double price, String description, String url) {
		super();
		this.id = id;
		this.serviceName = serviceName;
		this.price = price;
		this.description = description;
		this.url = url;
		this.status= "OK";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
