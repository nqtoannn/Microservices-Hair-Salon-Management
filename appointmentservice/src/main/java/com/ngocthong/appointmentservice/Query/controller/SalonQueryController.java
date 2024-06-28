package com.ngocthong.appointmentservice.Query.controller;

import com.ngocthong.appointmentservice.Query.model.ResponseObject;
import com.ngocthong.appointmentservice.Query.queries.GetAllSalonsQuery;
import com.ngocthong.commonservice.model.SalonResponseCommonModel;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/appointment")
public class SalonQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/salon/findAll")
    public ResponseEntity<ResponseObject> getAllSalon() {
        GetAllSalonsQuery getAllSalonsQuery = new GetAllSalonsQuery();
        List<SalonResponseCommonModel> appointmentResponses = queryGateway.query(
                getAllSalonsQuery,
                ResponseTypes.multipleInstancesOf(SalonResponseCommonModel.class)
        ).join();
        if (appointmentResponses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", appointmentResponses));
        }
    }


}
