package com.quoctoan.orderservice.command.api.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.quoctoan.orderservice.command.api.data.Orders;
import com.quoctoan.orderservice.command.api.data.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderEventsHandler {

	@Autowired 
	private OrderRepository orderRepository;
	
	@EventHandler
	public void on(OrderCreatedEvent event) {
		Orders order = new Orders();
		BeanUtils.copyProperties(event, order);
		orderRepository.save(order);	 
	}

	@EventHandler
	public void on(OrderUpdatedEvent event){
		Optional<Orders> order = orderRepository.findById(event.getId());
		Orders updateOrder = new Orders();
		if (order.isPresent()){
			updateOrder = order.get();
			updateOrder.setCustomerId(event.getCustomerId());
			updateOrder.setOrderStatus(event.getOrderStatus());
			updateOrder.setTotalPrice(event.getTotalPrice());
		}
		orderRepository.save(updateOrder);
	}
	@EventHandler
	public void on(OrderStatusUpdatedEvent event){
		Optional<Orders> order = orderRepository.findById(event.getId());
		Orders updateOrder = new Orders();
		if (order.isPresent()){
			updateOrder = order.get();
			updateOrder.setOrderStatus(event.getOrderStatus());
		}
		orderRepository.save(updateOrder);
	}

}
