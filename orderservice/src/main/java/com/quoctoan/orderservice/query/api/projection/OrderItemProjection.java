package com.quoctoan.orderservice.query.api.projection;

import com.ngocthong.commonservice.model.ProductItemResponseCommonModel;
import com.ngocthong.commonservice.query.GetDetailProductItemById;
import com.quoctoan.orderservice.command.api.data.OrderItem;
import com.quoctoan.orderservice.command.api.data.OrderItemRepository;
import com.quoctoan.orderservice.query.api.model.OrderItemResponseModel;
import com.quoctoan.orderservice.query.api.queries.GetOrderItemByOrderId;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderItemProjection {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private QueryGateway queryGateway;
    @QueryHandler
    public List<OrderItemResponseModel> handle(GetOrderItemByOrderId query){
        List<OrderItem> ordersList = orderItemRepository.findByOrderId(query.getOrderId());
        List<OrderItemResponseModel> listOrderResponseModel = new ArrayList<>();
        ordersList.forEach(s -> {
            OrderItemResponseModel model = new OrderItemResponseModel();
            BeanUtils.copyProperties(s, model);
            try {
                GetDetailProductItemById getDetailsProductItemQuery = new GetDetailProductItemById(s.getProductItemId());
                ProductItemResponseCommonModel productItemResponseCommonModel = queryGateway.query(getDetailsProductItemQuery,
                        ResponseTypes.instanceOf(ProductItemResponseCommonModel.class)).join();
                model.setDescription(productItemResponseCommonModel.getDescription());
                model.setImageUrl(productItemResponseCommonModel.getImageUrl());
                model.setProductItemName(productItemResponseCommonModel.getProductItemName());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            listOrderResponseModel.add(model);
        });
        return listOrderResponseModel;
    }
}
