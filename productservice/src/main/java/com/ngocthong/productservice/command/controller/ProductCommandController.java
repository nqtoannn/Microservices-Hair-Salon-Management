package com.ngocthong.productservice.command.controller;


import com.ngocthong.productservice.command.command.CreateProductCommand;
import com.ngocthong.productservice.command.command.UpdateProductCommand;
import com.ngocthong.productservice.command.model.ProductRequestModel;
import com.ngocthong.productservice.command.model.ProductUpdateModel;
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
public class ProductCommandController {

	@Autowired
	private CommandGateway commandGateway;

	@PostMapping("management/product/add")
	public ResponseEntity<Object> addProduct(@RequestBody ProductRequestModel model) {
		CreateProductCommand command =
				new CreateProductCommand(UUID.randomUUID().toString(), model.getName(), model.getDescription(), model.getImageUrl(), model.getCategoryId());
		commandGateway.sendAndWait(command);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("OK", "Successfully", ""));
	}
	@PutMapping("management/product/update")
	public ResponseEntity<Object> updateProduct(@RequestBody ProductUpdateModel model) {
		UpdateProductCommand command =
				new UpdateProductCommand(model.getId(), model.getName(), model.getDescription(), model.getImageUrl(), model.getCategoryId());
		commandGateway.sendAndWait(command);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("OK", "Successfully", ""));
	}
}
