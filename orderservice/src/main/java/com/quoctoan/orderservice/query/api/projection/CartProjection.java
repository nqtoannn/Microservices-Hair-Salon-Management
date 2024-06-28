package com.quoctoan.orderservice.query.api.projection;


import com.ngocthong.commonservice.model.ProductItemResponseCommonModel;
import com.ngocthong.commonservice.query.GetDetailProductItemById;
import com.quoctoan.orderservice.command.api.data.Cart;
import com.quoctoan.orderservice.command.api.data.CartRepository;
import com.quoctoan.orderservice.query.api.model.CartResponseModel;
import com.quoctoan.orderservice.query.api.queries.GetCartByCustomerIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartProjection {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private QueryGateway queryGateway;
    @QueryHandler
    public List<CartResponseModel> handle(GetCartByCustomerIdQuery query){
        List<Cart> listCarts = cartRepository.findAllByCustomerId(query.getCustomerId());
        List<CartResponseModel> listCartResponseModel = new ArrayList<>();
        listCarts.forEach(s -> {
            CartResponseModel model = new CartResponseModel();
            BeanUtils.copyProperties(s, model);
            try {
                GetDetailProductItemById getDetailsProductItemQuery = new GetDetailProductItemById(s.getProductItemId());
                ProductItemResponseCommonModel productItemResponseCommonModel = queryGateway.query(getDetailsProductItemQuery,
                        ResponseTypes.instanceOf(ProductItemResponseCommonModel.class)).join();
                model.setImageUrl(productItemResponseCommonModel.getImageUrl());
                model.setProductItemName(productItemResponseCommonModel.getProductItemName());
                model.setPrice(productItemResponseCommonModel.getPrice());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            listCartResponseModel.add(model);
        });
        return listCartResponseModel;
    }

}
