package com.ngocthong.productservice.query.controller;

import java.util.List;

import com.ngocthong.productservice.ProductserviceApplication;
import com.ngocthong.productservice.query.model.ProductResponseModel;
import com.ngocthong.productservice.query.queries.GetProductQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ngocthong.productservice.query.queries.GetAllProductsQuery;

@RestController
@RequestMapping("/api/v1/product")
public class ProductQueryController {

	@Autowired 
	private  QueryGateway queryGateway;

	private Logger logger =org.slf4j.LoggerFactory.getLogger(ProductserviceApplication.class);

	@GetMapping("/{productId}")
    public ProductResponseModel getProductDetail(@PathVariable String productId) {
        GetProductQuery getProductQuery = new GetProductQuery();
		getProductQuery.setId(productId);
        return queryGateway.query(getProductQuery,
                ResponseTypes.instanceOf(ProductResponseModel.class))
                .join();
    }
	@GetMapping("/findAll")
	public List<ProductResponseModel> getAllProduct(){
		GetAllProductsQuery getAllProductsQuery = new GetAllProductsQuery();
		return queryGateway.query(getAllProductsQuery, ResponseTypes.multipleInstancesOf(ProductResponseModel.class))
				.join();
	}
}
