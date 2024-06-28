	package com.quoctoan.hairservice.command.controller;

import java.util.UUID;

import com.quoctoan.hairservice.command.command.UpdateStatusServiceCommand;
import com.quoctoan.hairservice.command.model.ResponseObject;
import com.quoctoan.hairservice.command.model.ServiceHairUpdateModel;
import com.quoctoan.hairservice.command.model.ServiceUpdateStatusModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.quoctoan.hairservice.command.command.CreateServiceCommand;
import com.quoctoan.hairservice.command.command.DeleteServiceCommand;
import com.quoctoan.hairservice.command.command.UpdateServiceCommand;
import com.quoctoan.hairservice.command.model.ServiceHairRequestModel;


@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/hairservice")
public class ServiceHairCommandController {

	@Autowired
	private CommandGateway commandGateway;
	@PostMapping
	public ResponseEntity<Object> addService(@RequestBody ServiceHairRequestModel model) {
		String serviceId = UUID.randomUUID().toString();
		try{
			CreateServiceCommand command =
					new CreateServiceCommand(serviceId, model.getServiceName(), model.getPrice(), model.getDescription(), model.getUrl());
			commandGateway.sendAndWait(command);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", serviceId));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ERROR", e.getMessage(), ""));
		}
	}

	@PutMapping
	public ResponseEntity<Object> updateService(@RequestBody ServiceHairUpdateModel model) {
		try{
			UpdateServiceCommand command =
					new UpdateServiceCommand(model.getId(), model.getServiceName(), model.getPrice(), model.getDescription(), model.getUrl(), model.getStatus());
			commandGateway.sendAndWait(command);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", model.getId()));
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseObject("ERROR", e.getMessage(), ""));
		}
	}

	@PutMapping("/updateStatus")
	public ResponseEntity<Object> updateStatusService(@RequestBody ServiceUpdateStatusModel model) {
		try{
			UpdateStatusServiceCommand command =
					new UpdateStatusServiceCommand(model.getId(), model.getStatus());
			commandGateway.sendAndWait(command);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", model.getId()));
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseObject("ERROR", e.getMessage(), ""));
		}
	}

	@DeleteMapping("/{serviceId}")
	public ResponseEntity<Object> deleteService(@PathVariable Integer serviceId) {
		try{
			DeleteServiceCommand command =
					new DeleteServiceCommand(serviceId);
			commandGateway.sendAndWait(command);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", serviceId));
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseObject("ERROR", e.getMessage(), ""));
		}
	}
}