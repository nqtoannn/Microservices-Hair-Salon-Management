package com.quoctoan.orderservice.query.api.controller;

import com.ngocthong.commonservice.model.ProductItemResponseCommonModel;
import com.ngocthong.commonservice.query.GetDetailProductItemById;
import com.quoctoan.orderservice.command.api.model.ResponseObject;
import com.quoctoan.orderservice.query.api.model.CartResponseModel;
import com.quoctoan.orderservice.query.api.queries.GetCartByCustomerIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
public class CartQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{customerId}")
    public ResponseEntity<ResponseObject> getCartByCustomerId(@PathVariable String customerId) {
        try {
            GetCartByCustomerIdQuery getCartByCustomerIdQuery = new GetCartByCustomerIdQuery();
            getCartByCustomerIdQuery.setCustomerId(customerId);
            List<CartResponseModel> orderItemList = queryGateway.query(getCartByCustomerIdQuery, ResponseTypes.multipleInstancesOf(CartResponseModel.class))
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
