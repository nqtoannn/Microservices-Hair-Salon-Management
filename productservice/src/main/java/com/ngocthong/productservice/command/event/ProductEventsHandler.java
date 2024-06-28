package com.ngocthong.productservice.command.event;

import com.ngocthong.productservice.command.data.Category;
import com.ngocthong.productservice.command.data.Product;
import com.ngocthong.productservice.command.data.ProductRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

//import com.tanthanh.commonservice.event.BookRollBackStatusEvent;
//import com.tanthanh.commonservice.event.BookUpdateStatusEvent;

@Component
public class ProductEventsHandler {

    @Autowired
    private ProductRepository productRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        try {
            Product product = new Product();
            BeanUtils.copyProperties(event, product);
            productRepository.save(product);
        } catch (Exception e) {
            System.out.println("Loi" + e);
        }
    }

    @EventHandler
    public void on(ProductUpdatedEvent event) {
        Product product = productRepository.getById(event.getId());
        product.setName(event.getName());
        product.setDescription(event.getDescription());
        product.setImageUrl(event.getImageUrl());
        product.setCategoryId(event.getCategoryId());
        productRepository.save(product);
    }
//	@EventHandler
//    public void on(BookDeletedEvent event) {
//
//	        bookRepository.deleteById(event.getBookId());;
//    }
//	@EventHandler
//	public void on(BookUpdateStatusEvent event) {
//		Book book = bookRepository.getById(event.getBookId());
//		book.setIsReady(event.getIsReady());
//		bookRepository.save(book);
//	}
//	@EventHandler
//	public void on(BookRollBackStatusEvent event) {
//		Book book = bookRepository.getById(event.getBookId());
//		book.setIsReady(event.getIsReady());
//		bookRepository.save(book);
//	}
}
