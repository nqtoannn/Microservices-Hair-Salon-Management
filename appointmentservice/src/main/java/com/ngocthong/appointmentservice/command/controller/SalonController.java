package com.ngocthong.appointmentservice.command.controller;

import com.ngocthong.appointmentservice.Query.model.ResponseObject;
import com.ngocthong.appointmentservice.command.command.CreateSalonCommand;
import com.ngocthong.appointmentservice.command.command.UpdateSalonCommand;
import com.ngocthong.appointmentservice.command.model.SalonRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/appointment/salon")
public class SalonController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/addSalon")
    public ResponseEntity<Object> addSalon(@RequestBody SalonRequestModel salonRequestModel) {
        String salonId = UUID.randomUUID().toString();
        try{
            CreateSalonCommand command = new CreateSalonCommand(
                    salonId,
                    salonRequestModel.getName(),
                    salonRequestModel.getAddress(),
                    salonRequestModel.getPhoneNumber(),
                    true
            );
            commandGateway.sendAndWait(command);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", salonId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ERROR", e.getMessage(), ""));
        }
    }

    @PutMapping("/updateSalon")
    public ResponseEntity<Object> updateSalon(@RequestBody SalonRequestModel salonRequestModel) {
        try{
            UpdateSalonCommand command = new UpdateSalonCommand(
                    salonRequestModel.getId(),
                    salonRequestModel.getName(),
                    salonRequestModel.getAddress(),
                    salonRequestModel.getPhoneNumber(),
                    salonRequestModel.getIsActive()
            );
            commandGateway.sendAndWait(command);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Successfully", salonRequestModel.getId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ERROR", e.getMessage(), ""));
        }
    }


}
