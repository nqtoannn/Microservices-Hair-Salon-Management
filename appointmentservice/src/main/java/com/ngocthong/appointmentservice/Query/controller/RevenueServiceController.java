package com.ngocthong.appointmentservice.Query.controller;

import com.ngocthong.appointmentservice.Query.model.MonthlyRevenue;
import com.ngocthong.appointmentservice.Query.model.ResponseObject;
import com.ngocthong.appointmentservice.Query.queries.GetRevenueBetween;
import com.ngocthong.appointmentservice.Query.queries.GetRevenueByYear;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/appointment/revenue")
public class RevenueServiceController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/getAllRevenueServiceByYear/{year}")
    public ResponseEntity<ResponseObject> getAllRevenueServiceByYear(@PathVariable Integer year) {
        GetRevenueByYear getRevenueByYear = new GetRevenueByYear();
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

    @GetMapping("/getAllRevenueServiceBetween/{startMonth}/{endMonth}/{year}")
    public ResponseEntity<ResponseObject> getAllRevenueServiceBetween(@PathVariable Integer startMonth, @PathVariable Integer endMonth, @PathVariable Integer year) {
        GetRevenueBetween getRevenueBetween = new GetRevenueBetween();
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
