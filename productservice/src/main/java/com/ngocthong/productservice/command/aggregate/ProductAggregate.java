package com.ngocthong.productservice.command.aggregate;

import com.ngocthong.productservice.command.command.CreateProductCommand;
import com.ngocthong.productservice.command.command.UpdateProductCommand;
import com.ngocthong.productservice.command.event.ProductCreatedEvent;
import com.ngocthong.productservice.command.event.ProductUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private String imageUrl;
    private String description;
    private String categoryId;

    public ProductAggregate() {

    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        ProductCreatedEvent productCreatedEvent
                = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent);
    }
    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.imageUrl = event.getImageUrl();
        this.description = event.getDescription();
        this.categoryId = event.getCategoryId();
    }
     @CommandHandler
    public void handle(UpdateProductCommand updateProductCommand) {
         ProductUpdatedEvent productUpdatedEvent
                = new ProductUpdatedEvent();
        BeanUtils.copyProperties(updateProductCommand, productUpdatedEvent);
        AggregateLifecycle.apply(productUpdatedEvent);
    }
    @EventSourcingHandler
    public void on(ProductUpdatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.imageUrl = event.getImageUrl();
        this.categoryId = event.getCategoryId();
        this.description = event.getDescription();
    }
}
