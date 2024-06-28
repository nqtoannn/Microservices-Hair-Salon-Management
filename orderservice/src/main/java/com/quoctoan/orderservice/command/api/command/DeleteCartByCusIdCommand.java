package com.quoctoan.orderservice.command.api.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteCartByCusIdCommand {
    private String customerId;
}
