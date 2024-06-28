package com.ngocthong.productservice.command.event;

import com.ngocthong.productservice.command.data.ProductItem;
import com.ngocthong.productservice.command.data.ProductItemRepository;
import com.ngocthong.productservice.command.data.ProductRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductItemEventsHandler {
    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    ProductRepository productRepository;

    @EventHandler
    public void on(ProductItemCreateEvent event) {
        try {
            ProductItem productItem = new ProductItem();
            BeanUtils.copyProperties(event, productItem);
            productItem.setStatus("OK");
            productItemRepository.save(productItem);
        } catch (Exception e) {
            System.out.println("Loi" + e);
        }
    }

    @EventHandler
    public void on(ProductItemUpdateEvent event) {
        ProductItem productItem = productItemRepository.getById(event.getProductItemId());
        productItem.setPrice(event.getPrice());
        productItem.setProductItemName(event.getProductItemName());
        productItem.setDescription(event.getDescription());
        productItem.setStatus(event.getStatus());
        productItem.setImageUrl(event.getImageUrl());
        productItem.setQuantity(event.getQuantity());
        productItemRepository.save(productItem);
    }

    @EventHandler
    public void on(ProductItemQuantityUpdatedEvent event){
        Optional<ProductItem> productItem = productItemRepository.findById(event.getId());
        ProductItem updateProductItem = new ProductItem();
        if( productItem.isPresent()){
            updateProductItem = productItem.get();
            updateProductItem.setQuantity(updateProductItem.getQuantity()-event.getQuantity());
        }
        productItemRepository.save(updateProductItem);
    }

    @EventHandler
    public void on(UpdateStatusProductItemEvent event){
        Optional<ProductItem> productItem = productItemRepository.findById(event.getProductItemId());
        ProductItem updateProductItem = new ProductItem();
        if( productItem.isPresent()){
            updateProductItem = productItem.get();
            updateProductItem.setStatus(event.getStatus());
        }
        productItemRepository.save(updateProductItem);
    }


}
