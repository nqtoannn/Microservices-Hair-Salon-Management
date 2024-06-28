package com.quoctoan.hairservice.query.controller;

import java.util.List;
import java.util.Optional;

import com.quoctoan.hairservice.command.model.ResponseObject;
import com.quoctoan.hairservice.query.model.HairServiceResponseModel;

import com.quoctoan.hairservice.query.queries.GetServiceByNameQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.quoctoan.hairservice.HairserviceApplication;
import com.quoctoan.hairservice.query.queries.GetAllServicesQuery;
import com.quoctoan.hairservice.query.queries.GetServiceQuery;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/hairservice")
public class ServiceQueryController {

	@Autowired 
	private  QueryGateway queryGateway;

	@GetMapping("/{serviceId}")
    public  ResponseEntity<ResponseObject> getHairServiceDetail(@PathVariable String serviceId) {
        try {
			GetServiceQuery getServiceQuery = new GetServiceQuery();
			getServiceQuery.setId(serviceId);
			Optional<HairServiceResponseModel> hairService = Optional.ofNullable(queryGateway.query(getServiceQuery,
                    ResponseTypes.instanceOf(HairServiceResponseModel.class)).join());
			if (!hairService.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", hairService));
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ERROR", e.getMessage(), ""));
		}
    }

	@GetMapping("/search/{name}")
	public  ResponseEntity<ResponseObject> getHairServiceByName(@PathVariable String name) {
		try {
			GetServiceByNameQuery getServiceByNameQuery = new GetServiceByNameQuery();
			getServiceByNameQuery.setName(name);
			Optional<HairServiceResponseModel> hairService = Optional.ofNullable(queryGateway.query(getServiceByNameQuery,
					ResponseTypes.instanceOf(HairServiceResponseModel.class)).join());
			if (!hairService.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", hairService));
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ERROR", e.getMessage(), ""));
		}
	}

	@GetMapping("/findAll")
	public ResponseEntity<ResponseObject> getAllHairServices(){
		try{
			GetAllServicesQuery getAllHairServicesQuery = new GetAllServicesQuery();
			List<HairServiceResponseModel> hairServiceList = queryGateway.query(getAllHairServicesQuery, ResponseTypes.multipleInstancesOf(HairServiceResponseModel.class))
					.join();
			if (!hairServiceList.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", hairServiceList));
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Not found", "Not found", ""));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ERROR", e.getMessage(), ""));
		}
	}
}