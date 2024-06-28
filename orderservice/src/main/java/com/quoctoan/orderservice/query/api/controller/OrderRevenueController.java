package com.quoctoan.orderservice.query.api.controller;

import com.quoctoan.orderservice.command.api.model.ResponseObject;
import com.quoctoan.orderservice.query.api.model.MonthlyRevenue;
import com.quoctoan.orderservice.query.api.queries.GetRevenueOrderBetween;
import com.quoctoan.orderservice.query.api.queries.GetRevenueOrderByYear;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order/revenue")
public class OrderRevenueController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/getAllRevenueOrderByYear/{year}")
    public ResponseEntity<ResponseObject> getAllRevenueServiceByYear(@PathVariable Integer year) {
        GetRevenueOrderByYear getRevenueByYear = new GetRevenueOrderByYear();
        getRevenueByYear.setYear(year);

        List<MonthlyRevenue> resultList = queryGateway.query(
                getRevenueByYear,
                ResponseTypes.multipleInstancesOf(MonthlyRevenue.class)
        ).join();
        if (resultList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", resultList));
        }
    }
    @GetMapping("/getAllRevenueOrderBetween/{startMonth}/{endMonth}/{year}")
    public ResponseEntity<ResponseObject> getAllRevenueServiceBetween(@PathVariable Integer startMonth, @PathVariable Integer endMonth, @PathVariable Integer year) {
        GetRevenueOrderBetween getRevenueBetween = new GetRevenueOrderBetween();
        getRevenueBetween.setYear(year);
        getRevenueBetween.setStartMonth(startMonth);
        getRevenueBetween.setEndMonth(endMonth);
        List<MonthlyRevenue> resultList = queryGateway.query(
                getRevenueBetween,
                ResponseTypes.multipleInstancesOf(MonthlyRevenue.class)
        ).join();
        if (resultList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", resultList));
        }
    }
}
