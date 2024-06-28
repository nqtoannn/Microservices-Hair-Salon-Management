package com.ngocthong.productservice.command.aggregate;

import com.ngocthong.commonservice.command.UpdateQuantityProductItemCommand;
import com.ngocthong.productservice.command.command.CreateProductItemCommand;
import com.ngocthong.productservice.command.command.UpdateProductItemCommand;
import com.ngocthong.productservice.command.command.UpdateStatusProductItemCommand;
import com.ngocthong.productservice.command.event.ProductItemCreateEvent;
import com.ngocthong.productservice.command.event.ProductItemQuantityUpdatedEvent;
import com.ngocthong.productservice.command.event.ProductItemUpdateEvent;
import com.ngocthong.productservice.command.event.UpdateStatusProductItemEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class ProductItemAggregate {
    @AggregateIdentifier
    private String productItemId;
    private Double price;
    private String status;
    private String productId;
    private String imageUrl;    
    private String productItemName;
    private String description;
    private Integer quantity;
    private Integer warrantyTime;

    public ProductItemAggregate() {
    }

    @CommandHandler
    public ProductItemAggregate(CreateProductItemCommand createProductItemCommand) {
        ProductItemCreateEvent productItemCreateEvent = new ProductItemCreateEvent();
        BeanUtils.copyProperties(createProductItemCommand, productItemCreateEvent);
        AggregateLifecycle.apply(productItemCreateEvent);
    }

    @EventSourcingHandler
    public void on(ProductItemCreateEvent event) {
        this.productItemId = event.getProductItemId();
        this.price = event.getPrice();
        this.productItemName = event.getProductItemName();
        this.imageUrl = event.getImageUrl();
        this.description = event.getDescription();
        this.quantity = event.getQuantity();
        this.warrantyTime = event.getWarrantyTime();
    }

    @CommandHandler
    public void handle(UpdateProductItemCommand updateProductItemCommand) {
        ProductItemUpdateEvent productItemUpdateEvent = new ProductItemUpdateEvent();
        BeanUtils.copyProperties(updateProductItemCommand, productItemUpdateEvent);
        AggregateLifecycle.apply(productItemUpdateEvent);
    }

    @EventSourcingHandler
    public void on(ProductItemUpdateEvent event) {
        this.productItemId = event.getProductItemId();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();
        this.status = event.getStatus();
        this.productItemName = event.getProductItemName();
        this.imageUrl = event.getImageUrl();
        this.description = event.getDescription();
        this.warrantyTime = event.getWarrantyTime();
    }


    @CommandHandler
    public void handle(UpdateQuantityProductItemCommand updateQuantityProductItemCommand){
        ProductItemQuantityUpdatedEvent productItemQuantityUpdatedEvent = new ProductItemQuantityUpdatedEvent();
        BeanUtils.copyProperties(updateQuantityProductItemCommand,productItemQuantityUpdatedEvent);
        AggregateLifecycle.apply(productItemQuantityUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductItemQuantityUpdatedEvent event){
        this.productItemId = event.getId();
        this.quantity = event.getQuantity();
    }

    @CommandHandler
    public void handle(UpdateStatusProductItemCommand updateStatusProductItemCommand){
        UpdateStatusProductItemEvent updateStatusProductItemEvent = new UpdateStatusProductItemEvent();
        BeanUtils.copyProperties(updateStatusProductItemCommand,updateStatusProductItemEvent);
        AggregateLifecycle.apply(updateStatusProductItemEvent);
    }

    @EventSourcingHandler
    public void on(UpdateStatusProductItemEvent event){
        this.productItemId = event.getProductItemId();
        this.status = event.getStatus();
    }

}
