package com.quoctoan.hairservice.command.controller;

import com.quoctoan.hairservice.command.command.CreateNewsCommand;
import com.quoctoan.hairservice.command.command.CreateServiceCommand;
import com.quoctoan.hairservice.command.model.NewsModel;
import com.quoctoan.hairservice.command.model.ServiceHairRequestModel;
import com.quoctoan.hairservice.query.model.ResponseObject;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/hairservice/news")
public class NewsCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/addNews")
    public ResponseEntity<Object> addNews(@RequestBody NewsModel model) {
        CreateNewsCommand command =
                new CreateNewsCommand(UUID.randomUUID().toString(), model.getTitle(), model.getImageUrl(), model.getDescription());
        commandGateway.sendAndWait(command);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("OK", "Successfully", ""));
    }

}
