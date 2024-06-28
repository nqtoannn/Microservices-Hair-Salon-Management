package com.quoctoan.orderservice.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, String> {
    @Query(value = "select C from Cart C where C.customerId = :customerId")
    List<Cart> findAllByCustomerId(String customerId);

}
