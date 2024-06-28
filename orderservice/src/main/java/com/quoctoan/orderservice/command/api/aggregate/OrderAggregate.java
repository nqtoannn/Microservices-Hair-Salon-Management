package com.quoctoan.orderservice.command.api.aggregate;

import java.time.LocalDate;

import com.quoctoan.orderservice.command.api.command.UpdateOrderCommand;
import com.quoctoan.orderservice.command.api.command.UpdateOrderStatusCommand;
import com.quoctoan.orderservice.command.api.event.OrderStatusUpdatedEvent;
import com.quoctoan.orderservice.command.api.event.OrderUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.hibernate.sql.Update;
import org.springframework.beans.BeanUtils;

import com.quoctoan.orderservice.command.api.command.CreateOrderCommand;
import com.quoctoan.orderservice.command.api.event.OrderCreatedEvent;

@Aggregate
public class OrderAggregate {

	@AggregateIdentifier
	private String id;
    private LocalDate orderDate;
    private Double totalPrice;
    private String customerId;
    private String orderStatus;
    private String payId;
    
    public OrderAggregate() {
    	
    }
    
    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
    	OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
    	BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
    	AggregateLifecycle.apply(orderCreatedEvent);
    }
    
    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
    	this.id = orderCreatedEvent.getId();
    	this.customerId = orderCreatedEvent.getCustomerId();
    	this.orderDate = orderCreatedEvent.getOrderDate();
    	this.totalPrice = orderCreatedEvent.getTotalPrice();
    	this.orderStatus = orderCreatedEvent.getOrderStatus();
    	this.payId = orderCreatedEvent.getPayId();
    }

    @CommandHandler
    public void handle(UpdateOrderCommand updateOrderCommand) {
        OrderUpdatedEvent orderUpdatedEvent = new OrderUpdatedEvent();
        BeanUtils.copyProperties(updateOrderCommand, orderUpdatedEvent);
        AggregateLifecycle.apply(orderUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderUpdatedEvent orderUpdatedEvent){
        this.id = orderUpdatedEvent.getId();
        this.customerId = orderUpdatedEvent.getCustomerId();
        this.totalPrice = orderUpdatedEvent.getTotalPrice();
        this.orderStatus = orderUpdatedEvent.getOrderStatus();
        this.payId = orderUpdatedEvent.getPayId();
    }

    @CommandHandler
    public void handle(UpdateOrderStatusCommand command) {
        OrderStatusUpdatedEvent event = new OrderStatusUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderStatusUpdatedEvent event){
        this.id = event.getId();
        this.orderStatus = event.getOrderStatus();
    }

}
