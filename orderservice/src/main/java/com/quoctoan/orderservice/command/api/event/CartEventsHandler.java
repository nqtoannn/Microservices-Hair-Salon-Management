package com.quoctoan.orderservice.command.api.event;

import com.quoctoan.orderservice.command.api.command.DeleteCartByCusIdCommand;
import com.quoctoan.orderservice.command.api.data.Cart;
import com.quoctoan.orderservice.command.api.data.CartRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartEventsHandler {
    @Autowired
    private CartRepository cartRepository;

    @EventHandler
    public void on(CartCreatedEvent event) {
        Boolean check= Boolean.TRUE;
        List<Cart> list = cartRepository.findAll();
        for (Cart item : list) {
            if (item.getProductItemId().equals(event.getProductItemId()) && item.getCustomerId().equals(event.getCustomerId())) {
                item.setQuantity(item.getQuantity() + event.getQuantity());
                check = false;
                break;
            }
        }
        if (check){
            Cart cart = new Cart();
            BeanUtils.copyProperties(event, cart);
            cartRepository.save(cart);
        }
    }

    @EventHandler
    public void on(CartBuyNowCreatedEvent event) {
        Cart cart = new Cart();
        BeanUtils.copyProperties(event, cart);
        cartRepository.save(cart);
    }

    @EventHandler
    public void on(CartUpdatedEvent event) {
        Cart cart = cartRepository.getById(event.getId());
        cart.setId(event.getId());
        cart.setCustomerId(event.getCustomerId());
        cart.setProductItemId(event.getProductItemId());
        cart.setQuantity(event.getQuantity());
        cartRepository.save(cart);
    }

    @EventHandler
    public void on(CartDeleteEvent event) {
        cartRepository.deleteById(event.getId());
    }

    @EventHandler
    public void on(ListCartDeleteEvent event) {
        for( String cartId : event.getListCartId()){
            cartRepository.deleteById(cartId);
        }
    }

    @EventHandler
    public void on(CustomerCartDeletedEvent event){
        List<Cart> listCart = cartRepository.findAllByCustomerId(event.getCustomerId());
        for (Cart cart: listCart){
            cartRepository.deleteById(cart.getId());
        }
    }

}
