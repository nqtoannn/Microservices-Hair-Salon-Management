package com.quoctoan.orderservice.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    @Query("SELECT oi FROM OrderItem oi WHERE oi.orderId = :orderId AND oi.productItemId = :productItemId")
    OrderItem findByOrderIdAndProductItemId(String orderId, String productItemId);

    @Query(value = "select OI from OrderItem OI where OI.orderId = :orderId")
    List<OrderItem> findByOrderId(String orderId);

}
