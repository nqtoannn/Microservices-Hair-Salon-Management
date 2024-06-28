package com.quoctoan.hairservice.command.aggregate;

import com.quoctoan.hairservice.command.command.UpdateStatusServiceCommand;
import com.quoctoan.hairservice.command.event.UpdateServiceStatusEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.quoctoan.hairservice.command.command.CreateServiceCommand;
import com.quoctoan.hairservice.command.command.DeleteServiceCommand;
import com.quoctoan.hairservice.command.command.UpdateServiceCommand;
import com.quoctoan.hairservice.command.event.ServiceCreateEvent;
import com.quoctoan.hairservice.command.event.ServiceDeletedEvent;
import com.quoctoan.hairservice.command.event.ServiceUpdatedEvent;


@Aggregate
public class ServiceAggregate {
	@AggregateIdentifier
	private String id;
	
    private String serviceName;
    private Double price;
    private String description;
    private String url;
    private String status;
    
    protected ServiceAggregate() {
    	
    }
    
    @CommandHandler
    public ServiceAggregate(CreateServiceCommand createServiceCommand) {
    	ServiceCreateEvent serviceCreateEvent = new ServiceCreateEvent();
    	BeanUtils.copyProperties(createServiceCommand, serviceCreateEvent);
    	AggregateLifecycle.apply(serviceCreateEvent);
    }
    
    @CommandHandler
    public void handle(UpdateServiceCommand updateServiceCommand) {
    	ServiceUpdatedEvent serviceUpdatedEvent = new ServiceUpdatedEvent();
    	BeanUtils.copyProperties(updateServiceCommand, serviceUpdatedEvent);
    	AggregateLifecycle.apply(serviceUpdatedEvent);
    }
    
    @CommandHandler
    public void handle(DeleteServiceCommand deleteServiceCommand) {
    	ServiceDeletedEvent serviceDeletedEvent = new ServiceDeletedEvent();
    	BeanUtils.copyProperties(deleteServiceCommand, serviceDeletedEvent);
    	AggregateLifecycle.apply(serviceDeletedEvent);
    }

    @CommandHandler
    public void handle(UpdateStatusServiceCommand updateStatusServiceCommand) {
        UpdateServiceStatusEvent updateServiceStatusEvent = new UpdateServiceStatusEvent();
        BeanUtils.copyProperties(updateStatusServiceCommand, updateServiceStatusEvent);
        AggregateLifecycle.apply(updateServiceStatusEvent);
    }


    @EventSourcingHandler
    public void on(ServiceCreateEvent event) {
    	this.id = event.getId();
    	this.serviceName = event.getServiceName();
    	this.price = event.getPrice();
    	this.description = event.getDescription();
    	this.url = event.getUrl();
        this.status = event.getStatus();
    }
    
    @EventSourcingHandler
    public void on(ServiceUpdatedEvent event) {
    	this.id = event.getId();
    	this.serviceName = event.getServiceName();
    	this.price = event.getPrice();
    	this.description = event.getDescription();
    	this.url = event.getUrl();
    }

    @EventSourcingHandler
    public void on(UpdateStatusServiceCommand event) {
        this.id = event.getId();
        this.status = event.getStatus();
    }

        
    @EventSourcingHandler
    public void on(ServiceDeletedEvent event) {
    	this.id = event.getId();
    }
    
}
    
