package com.quoctoan.orderservice.command.api.controller;

import com.quoctoan.orderservice.command.api.command.*;
import com.quoctoan.orderservice.command.api.model.CartRequestModel;
import com.quoctoan.orderservice.command.api.model.ResponseObject;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/cart")
public class CartControllerCommand {
    @Autowired
    CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<Object> addToCart(@RequestBody CartRequestModel model) {
        String cartId = UUID.randomUUID().toString();
        try{
            CreateCartCommand command =
                    new CreateCartCommand(cartId,  model.getCustomerId() ,model.getProductItemId(), model.getQuantity());
            commandGateway.sendAndWait(command);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", cartId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ERROR", e.getMessage(), ""));
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateCart(@RequestBody CartRequestModel model) {
        try{
            UpdateCartCommand command =
                    new UpdateCartCommand(model.getId(), model.getCustomerId() ,model.getProductItemId(), model.getQuantity());
            commandGateway.sendAndWait(command);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", model.getId()));
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("ERROR", e.getMessage(), ""));
        }

    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Object> deleteCart(@PathVariable String cartId) {
        try{
            DeleteCartCommand command =
                    new DeleteCartCommand(cartId);
            commandGateway.sendAndWait(command);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully",cartId));
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("ERROR", e.getMessage(), ""));
        }

    }

    @DeleteMapping("/deleteAllCartByCustomerId/{customerId}")
    public ResponseEntity<Object> deleteCartByCustomerId(@PathVariable String customerId) {
        try{
            DeleteCartByCusIdCommand command =
                    new DeleteCartByCusIdCommand(customerId);
            commandGateway.sendAndWait(command);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully",customerId));
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("ERROR", e.getMessage(), ""));
        }

    }
}
