package com.quoctoan.orderservice.query.api.controller;

import com.quoctoan.orderservice.command.api.model.ResponseObject;
import com.quoctoan.orderservice.query.api.model.OrderItemResponseModel;
import com.quoctoan.orderservice.query.api.queries.GetOrderItemByOrderId;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order/orderItem")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
public class OrderItemQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseObject> getOrderItemByOrderId(@PathVariable String orderId){

        try {
            GetOrderItemByOrderId getOrderItemByOrderId = new GetOrderItemByOrderId();
            getOrderItemByOrderId.setOrderId(orderId);
            List<OrderItemResponseModel> orderItemList = queryGateway.query(getOrderItemByOrderId, ResponseTypes.multipleInstancesOf(OrderItemResponseModel.class))
                    .join();
            if (!orderItemList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", orderItemList));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ERROR", e.getMessage(), ""));
        }
    }
}
