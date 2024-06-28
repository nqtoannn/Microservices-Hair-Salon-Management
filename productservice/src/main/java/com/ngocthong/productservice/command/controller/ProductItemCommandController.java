package com.ngocthong.productservice.command.controller;

import com.ngocthong.productservice.command.command.CreateProductItemCommand;
import com.ngocthong.productservice.command.command.UpdateProductItemCommand;
import com.ngocthong.productservice.command.command.UpdateStatusProductItemCommand;
import com.ngocthong.productservice.command.model.ProductItemRequestModel;
import com.ngocthong.productservice.command.model.ProductItemUpdateModel;
import com.ngocthong.productservice.command.model.ProductItemUpdateStatusModel;
import com.ngocthong.productservice.query.model.ResponseObject;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/")
public class ProductItemCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("management/productItem/add")
    public ResponseEntity<Object> addProductItem(@RequestBody ProductItemRequestModel model) {
        String id = UUID.randomUUID().toString();
        CreateProductItemCommand command = new CreateProductItemCommand(
                id,
                model.getPrice(),
                model.getImageUrl(),
                model.getProductItemName(),
                model.getDescription(),
                model.getQuantity(),
                model.getWarrantyTime()
        );
        commandGateway.sendAndWait(command);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("OK", "Successfully", id));
    }

    @PutMapping("management/productItem/update")
    public ResponseEntity<Object> updateProductItem(@RequestBody ProductItemUpdateModel model) {
        UpdateProductItemCommand command = new UpdateProductItemCommand(
                model.getProductItemId(),
                model.getPrice(),
                model.getQuantity(),
                model.getStatus(),
                model.getImageUrl(),
                model.getProductItemName(),
                model.getDescription()
        );
        commandGateway.sendAndWait(command);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("OK", "Successfully", ""));
    }

    @PutMapping("management/productItem/updateStatus")
    public ResponseEntity<Object> updateStatusProductItem(@RequestBody ProductItemUpdateStatusModel model) {
        UpdateStatusProductItemCommand command = new UpdateStatusProductItemCommand(
                model.getId(),
                model.getStatus()
        );
        commandGateway.sendAndWait(command);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("OK", "Successfully", ""));
    }


}
