package com.quoctoan.hairservice.command.event;

import lombok.Data;

@Data
public class ServiceCreateEvent {
	private String id;
    private String serviceName;
    private Double price;
    private String description;
    private String url;
    private String status;
}
