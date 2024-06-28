package com.quoctoan.orderservice.command.api.aggregate;

import com.quoctoan.orderservice.command.api.command.CreateOrderItemCommand;
import com.quoctoan.orderservice.command.api.event.OrderItemCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Aggregate
public class OrderItemAggregate {
    @AggregateIdentifier
    private String id;
    private String orderId;
    private String productItemId;
    private Integer quantity;
    private Double price;
    public OrderItemAggregate(){

    }

    @CommandHandler
    public OrderItemAggregate(CreateOrderItemCommand createOrderItemCommand) {
        OrderItemCreatedEvent orderItemCreatedEvent = new OrderItemCreatedEvent();
        BeanUtils.copyProperties(createOrderItemCommand, orderItemCreatedEvent);
        AggregateLifecycle.apply(orderItemCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderItemCreatedEvent orderItemCreatedEvent) {
        this.id = UUID.randomUUID().toString();
        this.orderId = orderItemCreatedEvent.getOrderId();
        this.price = orderItemCreatedEvent.getPrice();
        this.productItemId = orderItemCreatedEvent.getProductItemId();
        this.quantity = orderItemCreatedEvent.getQuantity();
    }

}
