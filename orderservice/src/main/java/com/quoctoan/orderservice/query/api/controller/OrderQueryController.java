package com.quoctoan.orderservice.query.api.controller;

import com.quoctoan.orderservice.command.api.model.ResponseObject;
import com.quoctoan.orderservice.query.api.model.OrderResponseModel;
import com.quoctoan.orderservice.query.api.queries.GetAllOrders;
import com.quoctoan.orderservice.query.api.queries.GetOrderByCustomerId;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/order")
public class OrderQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/getAllOrdersByCustomerId/{customerId}")
    public ResponseEntity<ResponseObject> getOrderByCustomerId(@PathVariable String customerId){

        try {
            GetOrderByCustomerId getOrderByCustomerId = new GetOrderByCustomerId();
            getOrderByCustomerId.setCustomerId(customerId);
            List<OrderResponseModel> listOrder = queryGateway.query(getOrderByCustomerId, ResponseTypes.multipleInstancesOf(OrderResponseModel.class))
                    .join();
            if (!listOrder.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", listOrder));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ERROR", e.getMessage(), ""));
        }
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<ResponseObject> getAllOrder(){

        try {
            GetAllOrders getAllOrders = new GetAllOrders();
            List<OrderResponseModel> listOrder = queryGateway.query(getAllOrders, ResponseTypes.multipleInstancesOf(OrderResponseModel.class))
                    .join();
            if (!listOrder.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", listOrder));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ERROR", e.getMessage(), ""));
        }
    }

}
