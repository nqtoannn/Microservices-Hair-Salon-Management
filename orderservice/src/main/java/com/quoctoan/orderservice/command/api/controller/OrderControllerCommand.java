package com.quoctoan.orderservice.command.api.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.quoctoan.orderservice.command.api.command.*;
import com.quoctoan.orderservice.command.api.model.BuyNowRequestModel;
import com.quoctoan.orderservice.command.api.model.OrderUpdateStatusRequestModel;
import com.quoctoan.orderservice.command.api.model.ResponseObject;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quoctoan.orderservice.command.api.model.OrderRequestModel;


@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/order")
public class OrderControllerCommand {
	@Autowired
	CommandGateway commandGateway;
	private static final Logger logger = LoggerFactory.getLogger(OrderControllerCommand.class);
	@PostMapping
	public ResponseEntity<Object> addOrder(@RequestBody OrderRequestModel model) {
		LocalDate currentDate = LocalDate.now();
		String orderId = UUID.randomUUID().toString();
		String [] listCartId = model.getListCartId();
		try{
			CreateOrderCommand command =
					new CreateOrderCommand(orderId,currentDate,model.getTotalPrice(), model.getCustomerId(),"WAITING",model.getPayId() ,listCartId);
			commandGateway.sendAndWait(command);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", orderId));
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseObject("ERROR", e.getMessage(), ""));
		}
	}

//	@PostMapping("/buyNow")
//	public ResponseEntity<Object> buyNow(@RequestBody BuyNowRequestModel model) {
//		String cartId = UUID.randomUUID().toString();
//		CreateCartBuyNowCommand command1 = new CreateCartBuyNowCommand(cartId, model.getCustomerId(), model.getProductItemId(), 1);
//		commandGateway.sendAndWait(command1);
//		LocalDate currentDate = LocalDate.now();
//		String orderId = UUID.randomUUID().toString();
//		String[] cartIds = new String[1];
//		cartIds[0] = cartId;
//		try{
//			CreateOrderCommand command =
//					new CreateOrderCommand(orderId,currentDate,model.getTotalPrice(), model.getCustomerId(),"PROCESSING",model.getPayId() ,cartIds);
//			commandGateway.sendAndWait(command);
//			DeleteCartCommand deleteCartCommand = new DeleteCartCommand(cartId);
//			commandGateway.sendAndWait(deleteCartCommand);
//			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", orderId));
//		}catch (Exception e) {
//			System.out.println(e.getMessage());
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ResponseObject("ERROR", e.getMessage(), ""));
//		}
//	}

	@PostMapping("/buyNow")
	public ResponseEntity<ResponseObject> buyNow(@RequestBody BuyNowRequestModel model) {
		String cartId = createCart(model);
		LocalDate currentDate = LocalDate.now();
		String orderId = UUID.randomUUID().toString();
		String[] cartIds = new String[]{cartId};

		try {
			createOrder(orderId, currentDate, model, cartIds);
            //deleteCart(cartId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", cartId));
		} catch (Exception e) {
			logger.error("Error processing order: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseObject("ERROR", e.getMessage(), ""));
		}
	}

	private String createCart(BuyNowRequestModel model) {
		String cartId = UUID.randomUUID().toString();
		CreateCartBuyNowCommand command = new CreateCartBuyNowCommand(cartId, model.getCustomerId(), model.getProductItemId(), 1);
		commandGateway.sendAndWait(command);
		return cartId;
	}

	private void createOrder(String orderId, LocalDate currentDate, BuyNowRequestModel model, String[] cartIds) {
		CreateOrderCommand command = new CreateOrderCommand(orderId, currentDate, model.getTotalPrice(), model.getCustomerId(), "WAITING", model.getPayId(), cartIds);
		commandGateway.sendAndWait(command);
	}

    private void deleteCart(String cartId) {
        DeleteCartCommand command = new DeleteCartCommand(cartId);
        commandGateway.sendAndWait(command);
    }


	@PutMapping
	public ResponseEntity<Object> updateStatusOrder(@RequestBody OrderUpdateStatusRequestModel model) {
		try{
			UpdateOrderStatusCommand command =
					new UpdateOrderStatusCommand(model.getId(),model.getOrderStatus());
			commandGateway.sendAndWait(command);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", model.getId()));
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseObject("ERROR", e.getMessage(), ""));
		}
	}



}
