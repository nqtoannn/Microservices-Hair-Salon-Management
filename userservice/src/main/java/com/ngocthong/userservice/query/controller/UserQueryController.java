package com.ngocthong.userservice.query.controller;


import com.ngocthong.commonservice.model.UserResponseCommonModel;
import com.ngocthong.userservice.model.ResponseObject;
import com.ngocthong.userservice.query.queries.GetAllCustomerQuery;
import com.ngocthong.userservice.query.queries.GetAllEmployeesQuery;
import com.ngocthong.userservice.query.queries.GetUserByIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/user")
public class UserQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/findById/{userId}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable String userId) {
        GetUserByIdQuery getUserByIdQuery = new GetUserByIdQuery();
        getUserByIdQuery.setUserId(userId);
        UserResponseCommonModel user = queryGateway.query(getUserByIdQuery,
                        ResponseTypes.instanceOf(UserResponseCommonModel.class))
                .join();
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            List<UserResponseCommonModel> rsList = new ArrayList<>();
            rsList.add(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", rsList));
        }
    }

    @GetMapping("/getAllEmployees")
    public  ResponseEntity<ResponseObject> getAllEmployees(){
        GetAllEmployeesQuery getAllEmployees = new GetAllEmployeesQuery();
        List<UserResponseCommonModel> list = queryGateway.query(getAllEmployees,
                ResponseTypes.multipleInstancesOf(UserResponseCommonModel.class)).join();
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", list));
        }
    }

    @GetMapping("/getAllCustomer")
    public  ResponseEntity<ResponseObject> getAllCustomer(){
        GetAllCustomerQuery getAllCustomerQuery = new GetAllCustomerQuery();
        List<UserResponseCommonModel> list = queryGateway.query(getAllCustomerQuery,
                ResponseTypes.multipleInstancesOf(UserResponseCommonModel.class)).join();
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", list));
        }
    }

}
