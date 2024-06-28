package com.ngocthong.productservice.query.projection;

import java.util.ArrayList;
import java.util.List;

import com.ngocthong.productservice.command.data.Product;
import com.ngocthong.productservice.command.data.ProductRepository;
import com.ngocthong.productservice.query.model.ProductResponseModel;
import com.ngocthong.productservice.query.queries.GetAllProductsQuery;
import com.ngocthong.productservice.query.queries.GetProductQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProductProjection {
	@Autowired
	private ProductRepository productRepository;

	 @QueryHandler
	    public ProductResponseModel handle(GetProductQuery getProductQuery) {
         ProductResponseModel model = new ProductResponseModel();
		 Product product = productRepository.getById(getProductQuery.getId());
	      BeanUtils.copyProperties(product, model);
		  model.setCategoryId(product.getCategoryId());
	        return model;
	    }
	 @QueryHandler
	 List<ProductResponseModel> handle(GetAllProductsQuery getAllBooksQuery){
		 List<Product> listEntity = productRepository.findAll();
		 List<ProductResponseModel> listProducts = new ArrayList<>();
		 listEntity.forEach(s -> {
             ProductResponseModel model = new ProductResponseModel();
			 BeanUtils.copyProperties(s, model);
			 model.setCategoryId(s.getCategoryId());
             listProducts.add(model);
		 });
		 return listProducts;
	 }

	// @QueryHandler
	// public ProductItemResponseCommonModel handle(GetDetailsProductItemQuery getDetailsProductItemQuery) {
	// 	System.out.println("id: " + getDetailsProductItemQuery.getProductItemId());
	// 	ProductItem productItem = productItemRepository.getById(getDetailsProductItemQuery.getProductItemId());
	// 	ProductItemResponseCommonModel model = new ProductItemResponseCommonModel();
	// 	BeanUtils.copyProperties(productItem, model);
	// 	return model;
	// }


}
