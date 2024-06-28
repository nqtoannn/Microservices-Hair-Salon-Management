package com.quoctoan.hairservice.query.controller;

import com.quoctoan.hairservice.query.model.NewsResponseModel;
import com.quoctoan.hairservice.query.model.ResponseObject;
import com.quoctoan.hairservice.query.queries.GetAllNews;
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
@RequestMapping("/api/v1/hairservice")
public class NewsQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/news/findAll")
    public ResponseEntity<ResponseObject> getAllNews(){
        GetAllNews getAllNews = new GetAllNews();
        List<NewsResponseModel> resultList = queryGateway.query(getAllNews, ResponseTypes.multipleInstancesOf(NewsResponseModel.class))
                .join();
        if (resultList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", resultList));
        }
    }

}
