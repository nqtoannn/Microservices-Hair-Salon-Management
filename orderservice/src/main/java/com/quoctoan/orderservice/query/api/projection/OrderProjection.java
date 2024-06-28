package com.quoctoan.orderservice.query.api.projection;

import com.ngocthong.commonservice.model.ProductItemResponseCommonModel;
import com.ngocthong.commonservice.model.UserResponseCommonModel;
import com.ngocthong.commonservice.query.GetDetailProductItemById;
import com.ngocthong.commonservice.query.GetDetailUser;
import com.quoctoan.orderservice.command.api.data.OrderItem;
import com.quoctoan.orderservice.command.api.data.OrderItemRepository;
import com.quoctoan.orderservice.command.api.data.OrderRepository;
import com.quoctoan.orderservice.command.api.data.Orders;
import com.quoctoan.orderservice.query.api.model.MonthlyRevenue;
import com.quoctoan.orderservice.query.api.model.OrderItemResponseModel;
import com.quoctoan.orderservice.query.api.model.OrderResponseModel;
import com.quoctoan.orderservice.query.api.queries.GetAllOrders;
import com.quoctoan.orderservice.query.api.queries.GetOrderByCustomerId;
import com.quoctoan.orderservice.query.api.queries.GetRevenueOrderBetween;
import com.quoctoan.orderservice.query.api.queries.GetRevenueOrderByYear;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class OrderProjection {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private QueryGateway queryGateway;
    @QueryHandler
    public List<OrderResponseModel> handle(GetOrderByCustomerId query){
        List<Orders> ordersList = orderRepository.findAllByCustomerId(query.getCustomerId());
        List<OrderResponseModel> ordersResponses = new ArrayList<>();
        ordersList.forEach(o -> {
            OrderResponseModel orderResponseModel = new OrderResponseModel();
            orderResponseModel.setStatus(o.getOrderStatus());
            BeanUtils.copyProperties(o,orderResponseModel);
            try {
                GetDetailUser getDetailUser = new GetDetailUser(o.getCustomerId());
                UserResponseCommonModel userResponseCommonModel = queryGateway.query(getDetailUser, ResponseTypes.instanceOf(
                        UserResponseCommonModel.class
                )).join();
                orderResponseModel.setCustomerName(userResponseCommonModel.getFullName());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            List<OrderItem> orderItemsList = orderItemRepository.findByOrderId(o.getId());
            List<OrderItemResponseModel> listOrderResponseModel = new ArrayList<>();
            orderItemsList.forEach(s -> {
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
            orderResponseModel.setOrderItems(listOrderResponseModel);
            ordersResponses.add(orderResponseModel);
        });
        return ordersResponses;
    }

    @QueryHandler
    public List<OrderResponseModel> handle(GetAllOrders query){
        List<Orders> ordersList = orderRepository.findAll();
        List<OrderResponseModel> ordersResponses = new ArrayList<>();
        ordersList.forEach(o -> {
            OrderResponseModel orderResponseModel = new OrderResponseModel();
            orderResponseModel.setStatus(o.getOrderStatus());
            BeanUtils.copyProperties(o,orderResponseModel);
            try {
                GetDetailUser getDetailUser = new GetDetailUser(o.getCustomerId());
                UserResponseCommonModel userResponseCommonModel = queryGateway.query(getDetailUser, ResponseTypes.instanceOf(
                        UserResponseCommonModel.class
                )).join();
                orderResponseModel.setCustomerName(userResponseCommonModel.getFullName());
                List<OrderItem> orderItemsList = orderItemRepository.findByOrderId(o.getId());
                List<OrderItemResponseModel> listOrderResponseModel = new ArrayList<>();
                orderItemsList.forEach(s -> {
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
                orderResponseModel.setOrderItems(listOrderResponseModel);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            ordersResponses.add(orderResponseModel);
        });
        return ordersResponses;
    }


    @QueryHandler
    public List<MonthlyRevenue> handle(GetRevenueOrderBetween getRevenueBetween) {
        // Fetch the results from the repository
        List<Object[]> results = orderRepository.findMonthlyRevenueByYearAndMonths(
                getRevenueBetween.getYear(),
                getRevenueBetween.getStartMonth(),
                getRevenueBetween.getEndMonth()
        );

        // Initialize a map to hold monthly revenues, defaulting to 0.0
        Map<Integer, Double> monthlyRevenueMap = new HashMap<>();
        for (int month = getRevenueBetween.getStartMonth(); month <= getRevenueBetween.getEndMonth(); month++) {
            monthlyRevenueMap.put(month, 0.0);
        }

        // Populate the map with actual revenue data from the results
        for (Object[] result : results) {
            int month = (int) result[1];
            double totalRevenue = ((Number) result[2]).doubleValue();
            monthlyRevenueMap.put(month, totalRevenue);
        }

        // Convert the map entries to a list of MonthlyRevenue objects
        List<MonthlyRevenue> monthlyRevenues = monthlyRevenueMap.entrySet().stream()
                .map(entry -> new MonthlyRevenue(getRevenueBetween.getYear(), entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // Return the list sorted by month for consistency
        monthlyRevenues.sort(Comparator.comparingInt(MonthlyRevenue::getMonth));

        return monthlyRevenues;
    }

    @QueryHandler
    public List<MonthlyRevenue> handle(GetRevenueOrderByYear getRevenueOrderByYear) {
        List<Object[]> results = orderRepository.findMonthlyRevenueByYear(getRevenueOrderByYear.getYear());
        List<MonthlyRevenue> monthlyRevenues = new ArrayList<>();
        for (Object[] result : results) {
            int month = (int) result[1];
            double totalRevenue = ((Number) result[2]).doubleValue();
            monthlyRevenues.add(new MonthlyRevenue(getRevenueOrderByYear.getYear(), month, totalRevenue));
        }
        return monthlyRevenues;
    }
   
}

