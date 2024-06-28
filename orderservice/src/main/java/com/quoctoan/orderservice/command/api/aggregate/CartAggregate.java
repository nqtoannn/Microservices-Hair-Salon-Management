package com.quoctoan.orderservice.command.api.aggregate;

import com.quoctoan.orderservice.command.api.command.*;
import com.quoctoan.orderservice.command.api.event.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class CartAggregate {
    @AggregateIdentifier
    private String id;
    private String customerId;
    private String productItemId;
    private Integer quantity;

    public CartAggregate(){

    }
    @CommandHandler
    public  CartAggregate(CreateCartCommand createCartCommand) {
        CartCreatedEvent cartCreatedEvent = new CartCreatedEvent();
        BeanUtils.copyProperties(createCartCommand, cartCreatedEvent);
        AggregateLifecycle.apply(cartCreatedEvent);
    }

    @EventSourcingHandler
    public void on(CartCreatedEvent cartCreatedEvent) {
        this.id = cartCreatedEvent.getId();
        this.customerId = cartCreatedEvent.getCustomerId();
        this.productItemId = cartCreatedEvent.getProductItemId();
        this.quantity = cartCreatedEvent.getQuantity();
    }

    @CommandHandler
    public  CartAggregate(CreateCartBuyNowCommand createCartCommand) {
        CartBuyNowCreatedEvent cartCreatedEvent = new CartBuyNowCreatedEvent();
        BeanUtils.copyProperties(createCartCommand, cartCreatedEvent);
        AggregateLifecycle.apply(cartCreatedEvent);
    }

    @EventSourcingHandler
    public void on(CartBuyNowCreatedEvent cartCreatedEvent) {
        this.id = cartCreatedEvent.getId();
        this.customerId = cartCreatedEvent.getCustomerId();
        this.productItemId = cartCreatedEvent.getProductItemId();
        this.quantity = cartCreatedEvent.getQuantity();
    }

    @CommandHandler
    public void handle(UpdateCartCommand updateCartCommand) {
        CartUpdatedEvent cartUpdatedEvent = new CartUpdatedEvent();
        BeanUtils.copyProperties(updateCartCommand, cartUpdatedEvent);
        AggregateLifecycle.apply(cartUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(CartUpdatedEvent event) {
        this.id = event.getId();
        this.customerId = event.getCustomerId();
        this.productItemId = event.getProductItemId();
        this.quantity = event.getQuantity();
    }

    @CommandHandler
    public void handle(DeleteCartCommand deleteCartCommand) {
        CartDeleteEvent cartDeleteEvent = new CartDeleteEvent();
        BeanUtils.copyProperties(deleteCartCommand, cartDeleteEvent);
        AggregateLifecycle.apply(cartDeleteEvent);
    }

    @EventSourcingHandler
    public void on(CartDeleteEvent event) {
        this.id = event.getId();
    }

    @CommandHandler
    public void handle(DeleteListCartCommand deleteListCartCommand){
        ListCartDeleteEvent listCartDeleteEvent = new ListCartDeleteEvent();
        BeanUtils.copyProperties(deleteListCartCommand,listCartDeleteEvent);
        AggregateLifecycle.apply(listCartDeleteEvent);
    }

    @CommandHandler
    public void handle(DeleteCartByCusIdCommand command){
        CustomerCartDeletedEvent event = new CustomerCartDeletedEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }
}
