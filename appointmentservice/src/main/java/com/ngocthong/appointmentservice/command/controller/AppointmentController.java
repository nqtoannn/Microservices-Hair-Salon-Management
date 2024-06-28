package com.ngocthong.appointmentservice.command.controller;

import com.ngocthong.appointmentservice.Query.model.ResponseObject;
import com.ngocthong.appointmentservice.command.command.CreateAppointmentCommand;
import com.ngocthong.appointmentservice.command.command.UpdateAppointmentCommand;
import com.ngocthong.appointmentservice.command.command.UpdateStatusAppointmentCommand;
import com.ngocthong.appointmentservice.command.model.AppointmentRequestModel;
import com.ngocthong.appointmentservice.command.model.AppointmentUpdateModel;
import com.ngocthong.appointmentservice.command.model.AppointmentUpdateStatusModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/add")
    public ResponseEntity<Object> addAppointment(@RequestBody AppointmentRequestModel model) {
        CreateAppointmentCommand command = new CreateAppointmentCommand(
                UUID.randomUUID().toString(),
                model.getAppointmentDate(),
                model.getAppointmentTime(),
                model.getPrice(),
                model.getCustomerId(),
                model.getSalonId(),
                model.getServiceId(),
                model.getUserId()
        );
        commandGateway.sendAndWait(command);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("OK", "Successfully", ""));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateAppointment(@RequestBody AppointmentUpdateModel model) {
        UpdateAppointmentCommand command = new UpdateAppointmentCommand(
                model.getAppointmentId(),
                model.getAppointmentDate(),
                model.getAppointmentTime(),
                model.getSalonId(),
                model.getServiceId(),
                model.getUserId()
        );
        commandGateway.sendAndWait(command);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("OK", "Successfully", ""));
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<Object> updateStatusAppointment(@RequestBody AppointmentUpdateStatusModel model) {
        UpdateStatusAppointmentCommand command = new UpdateStatusAppointmentCommand(
                model.getAppointmentId(),
                model.getStatus()
        );
        commandGateway.sendAndWait(command);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("OK", "Successfully", ""));
    }

}
