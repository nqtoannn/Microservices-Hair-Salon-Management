package com.ngocthong.appointmentservice.Query.controller;

import com.ngocthong.appointmentservice.Query.model.AppointmentResponse;
import com.ngocthong.appointmentservice.Query.model.ResponseObject;
import com.ngocthong.appointmentservice.Query.queries.*;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/findAll")
    public ResponseEntity<ResponseObject> getAllAppointments() {
        GetAllAppointmentsQuery getAllAppointmentsQuery = new GetAllAppointmentsQuery();
        List<AppointmentResponse> appointmentResponses = queryGateway.query(
                getAllAppointmentsQuery,
                ResponseTypes.multipleInstancesOf(AppointmentResponse.class)
        ).join();

        if (appointmentResponses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", appointmentResponses));
        }
    }
    @GetMapping("/findByCustomerId/{customerId}")
    public ResponseEntity<ResponseObject> getAllAppointmentsByCustomerId(@PathVariable String customerId) {
        GetAllAppointmentByCustomerIdQuery getAllAppointmentByCustomerIdQuery = new GetAllAppointmentByCustomerIdQuery();
        getAllAppointmentByCustomerIdQuery.setCustomerId(customerId);
        List<AppointmentResponse> appointmentResponses = queryGateway.query(
                getAllAppointmentByCustomerIdQuery,
                ResponseTypes.multipleInstancesOf(AppointmentResponse.class)
        ).join();
        if (appointmentResponses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", appointmentResponses));
        }
    }

    @GetMapping("/findWaitingByEmployeeId/{employeeId}")
    public ResponseEntity<ResponseObject> getAllAppointmentsByEmployeeId(@PathVariable String employeeId) {
        GetAppointmentByEmployeeIdQuery getAppointmentByEmployeeIdQuery = new GetAppointmentByEmployeeIdQuery();
        getAppointmentByEmployeeIdQuery.setEmployeeId(employeeId);
        List<AppointmentResponse> appointmentResponses = queryGateway.query(
                getAppointmentByEmployeeIdQuery,
                ResponseTypes.multipleInstancesOf(AppointmentResponse.class)
        ).join();
        if (appointmentResponses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", appointmentResponses));
        }
    }

    @GetMapping("/findAllByEmployeeId/{employeeId}")
    public ResponseEntity<ResponseObject> findAllByEmployeeId(@PathVariable String employeeId) {
        GetAllAppointmentByEmployeeIdQuery getAppointmentByEmployeeIdQuery = new GetAllAppointmentByEmployeeIdQuery();
        getAppointmentByEmployeeIdQuery.setEmployeeId(employeeId);
        List<AppointmentResponse> appointmentResponses = queryGateway.query(
                getAppointmentByEmployeeIdQuery,
                ResponseTypes.multipleInstancesOf(AppointmentResponse.class)
        ).join();
        if (appointmentResponses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", appointmentResponses));
        }
    }

    @GetMapping("/findDoneByEmployeeId/{employeeId}")
    public ResponseEntity<ResponseObject> getDoneAppointmentsByEmployeeId(@PathVariable String employeeId) {
        GetAppointmentDoneByEmployeeIdQuery getAppointmentDoneByEmployeeIdQuery = new GetAppointmentDoneByEmployeeIdQuery();
        getAppointmentDoneByEmployeeIdQuery.setEmployeeId(employeeId);
        List<AppointmentResponse> appointmentResponses = queryGateway.query(
                getAppointmentDoneByEmployeeIdQuery,
                ResponseTypes.multipleInstancesOf(AppointmentResponse.class)
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
