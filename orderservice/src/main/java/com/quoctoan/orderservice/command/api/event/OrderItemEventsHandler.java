package com.quoctoan.orderservice.command.api.event;

import com.quoctoan.orderservice.command.api.data.*;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OrderItemEventsHandler {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @EventHandler
    public void on(OrderItemCreatedEvent event) {
        OrderItem orderItem = orderItemRepository.findByOrderIdAndProductItemId(event.getOrderId(), event.getProductItemId());
        if (orderItem == null) {
            OrderItem orderItemNew = new OrderItem();
            orderItemNew.setId(event.getId());
            orderItemNew.setOrderId(event.getOrderId());
            orderItemNew.setProductItemId(event.getProductItemId());
            orderItemNew.setPrice(event.getPrice());
            orderItemNew.setQuantity(event.getQuantity());
            orderItemRepository.save(orderItemNew);
        }
    }

    @EventHandler
    public void on(OrderDeletedEvent event){
        if(orderItemRepository.findById(event.getId()).isPresent()) {
            orderItemRepository.deleteById(event.getId());
        }
        else return;
    }
}
