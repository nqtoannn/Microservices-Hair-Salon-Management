package com.quoctoan.orderservice.query.api.queries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCartByCustomerIdQuery {
    private String customerId;
}
