package com.ngocthong.productservice.query.projection;


import com.ngocthong.commonservice.model.ProductItemResponseCommonModel;
import com.ngocthong.commonservice.query.GetDetailProductItemById;
import com.ngocthong.productservice.command.data.ProductItem;
import com.ngocthong.productservice.command.data.ProductItemRepository;
import com.ngocthong.productservice.query.model.ProductItemResponseModel;
import com.ngocthong.productservice.query.queries.GetAllProductItemQuery;
import com.ngocthong.productservice.query.queries.GetProductItemByIdQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductItemProjection {
    @Autowired
    private ProductItemRepository productItemRepository;

    @QueryHandler
    public ProductItemResponseModel handle(GetProductItemByIdQuery getProductItemById) {
        String productItemId = getProductItemById.getProductItemId();
        ProductItem productItem = productItemRepository.getById(productItemId);
        ProductItemResponseModel model = new ProductItemResponseModel();
        BeanUtils.copyProperties(productItem, model);
        return model;
    }

    @QueryHandler
    public ProductItemResponseCommonModel handle(GetDetailProductItemById query) {
        String productItemId = query.getProductItemId();
        ProductItem productItem = productItemRepository.getById(productItemId);
        ProductItemResponseCommonModel model = new ProductItemResponseCommonModel();
        BeanUtils.copyProperties(productItem, model);
        return model;
    }

    @QueryHandler
    List<ProductItemResponseModel> handle(GetAllProductItemQuery getAllProductItemQuery){
        List<ProductItem> listEntity = productItemRepository.findAll();
        List<ProductItemResponseModel> listProductItems = new ArrayList<>();
        listEntity.forEach(s -> {
            ProductItemResponseModel model = new ProductItemResponseModel();
            BeanUtils.copyProperties(s, model);
            listProductItems.add(model);
        });
        return listProductItems;
    }

}
