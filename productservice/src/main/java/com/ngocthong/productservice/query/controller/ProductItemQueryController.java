package com.ngocthong.productservice.query.controller;

import com.ngocthong.commonservice.query.GetDetailProductItemById;
import com.ngocthong.productservice.query.model.ProductItemResponseModel;
import com.ngocthong.productservice.query.model.ResponseObject;
import com.ngocthong.productservice.query.queries.GetAllProductItemQuery;
import com.ngocthong.productservice.query.queries.GetProductItemByIdQuery;
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
@RequestMapping("/api/v1/productItem")
public class ProductItemQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/findAll")
    public ResponseEntity<ResponseObject> getAllProductItems() {
        GetAllProductItemQuery getAllAppointmentsQuery = new GetAllProductItemQuery();
        List<ProductItemResponseModel> productItemResponseModelList = queryGateway.query(
                getAllAppointmentsQuery,
                ResponseTypes.multipleInstancesOf(ProductItemResponseModel.class)
        ).join();

        if (productItemResponseModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", productItemResponseModelList));
        }
    }

//    @GetMapping("/findById/{productItemId}")
//    public ResponseEntity<ResponseObject> getProductItemById(@PathVariable String productItemId) {
//        GetProductItemByIdQuery getProductItemById = new GetProductItemByIdQuery();
//        getProductItemById.setProductItemId(productItemId);
//        ProductItemResponseModel productItemResponseModel = queryGateway.query(
//                getProductItemById,
//                ProductItemResponseModel.class
//        ).join();
//        if (productItemResponseModel == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ResponseObject("Not found", "NOT found", ""));
//        } else {
//            List<ProductItemResponseModel> rsList = new ArrayList<>();
//            rsList.add(productItemResponseModel);
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(new ResponseObject("OK", "Successfully", rsList));
//        }
//    }
    @GetMapping("/findById/{productItemId}")
    public ResponseEntity<ResponseObject> getProductItemById(@PathVariable String productItemId) {
        GetProductItemByIdQuery getProductItemById = new GetProductItemByIdQuery();
        getProductItemById.setProductItemId(productItemId);
        ProductItemResponseModel productItemResponseModel = queryGateway.query(
                getProductItemById,
                ProductItemResponseModel.class
        ).join();
        if (productItemResponseModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "NOT found", ""));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "Successfully", productItemResponseModel));
        }
    }

}
