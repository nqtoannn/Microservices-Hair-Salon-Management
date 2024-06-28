package com.quoctoan.orderservice.command.api.saga;

import com.ngocthong.commonservice.command.UpdateQuantityProductItemCommand;
import com.ngocthong.commonservice.model.ProductItemResponseCommonModel;
import com.ngocthong.commonservice.query.GetDetailProductItemById;
import com.quoctoan.orderservice.command.api.command.CreateOrderItemCommand;
import com.quoctoan.orderservice.command.api.command.DeleteCartCommand;
import com.quoctoan.orderservice.command.api.command.UpdateOrderCommand;
import com.quoctoan.orderservice.command.api.data.*;
import com.quoctoan.orderservice.command.api.event.OrderCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Saga
public class OrderSaga {

    @Autowired
    private transient QueryGateway queryGateway;

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private CartRepository cartRepository;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void handle(OrderCreatedEvent event) {
        try {
            Boolean checkOrder = true;
            String[] listCartId = event.getListCart();
            List<Cart> listCart = new ArrayList<>();
            List<ProductItemResponseCommonModel> listProductItem = new ArrayList<>();
            for (String cartId : listCartId) {
                try {
                    Cart cart = cartRepository.getById(cartId);
                    listCart.add(cart);
                    listProductItem.add(getProductItem(cart.getProductItemId()));
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    endSaga();
                    break;
                }
            }
            for(int i=0; i< listCart.size();i++){
                if (listCart.get(i).getQuantity() > listProductItem.get(i).getQuantity() || listProductItem.get(i).getStatus().equals("NOT_OK")){
                    checkOrder = false;
                };
            }
            String customerId = listCart.get(0).getCustomerId();
            if (checkOrder){
                createOrderItem(listCart,listProductItem, event.getId());
                updateQuantityProductItem(listCart,listProductItem);
                deleteListCart(listCartId);
                Orders order = new Orders("1111", LocalDate.now(), event.getTotalPrice(), customerId,"WAITING","Null");
                updateOrder(order, event.getId());
//                updateQuantityProductItem(listCart,listProductItem);
            }
            else {
                Orders order = new Orders("1111", LocalDate.now(), event.getTotalPrice(), customerId,"REJECTED","Null");
                updateOrder(order, event.getId());
            }
            SagaLifecycle.end();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void createOrderItem (List<Cart> cartList, List<ProductItemResponseCommonModel> productItemList, String id){
        for (int i=0;i <cartList.size();i++){
            CreateOrderItemCommand createOrderItemCommand = new CreateOrderItemCommand(UUID.randomUUID().toString(),id, productItemList.get(i).getProductItemId(),productItemList.get(i).getPrice(),cartList.get(i).getQuantity());
            commandGateway.sendAndWait(createOrderItemCommand);
            SagaLifecycle.end();
        }
    }

    private void updateOrder(Orders order,String id){
        SagaLifecycle.associateWith("id", id);
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand(id, order.getTotalPrice(), order.getCustomerId(),order.getOrderStatus(), order.getPayId());
        commandGateway.sendAndWait(updateOrderCommand);
        SagaLifecycle.end();
    }


    private void updateQuantityProductItem (List<Cart> cartList, List<ProductItemResponseCommonModel> productItemList){
        for (int i=0;i <cartList.size();i++){
            System.out.println(productItemList.get(i).getProductItemId());
            UpdateQuantityProductItemCommand updateQuantityProductItemCommand = new UpdateQuantityProductItemCommand(productItemList.get(i).getProductItemId(),cartList.get(i).getQuantity());
            commandGateway.sendAndWait(updateQuantityProductItemCommand);
        }
    }


    private ProductItemResponseCommonModel getProductItem(String productItemId){
        GetDetailProductItemById getDetailsProductItemQuery = new GetDetailProductItemById(productItemId);
        return queryGateway.query(getDetailsProductItemQuery, ResponseTypes.instanceOf(ProductItemResponseCommonModel.class)).join();
    }

    private void deleteListCart(String[] listCartId){
        System.out.println("Zo day nua");
        for (String cartId : listCartId) {
            try {
                System.out.println(cartId);
                DeleteCartCommand deleteCartCommand = new DeleteCartCommand(cartId);
                commandGateway.sendAndWait(deleteCartCommand);
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                endSaga();
                break;
            }
        }
    }

    @EndSaga
    private void endSaga(){
    }

}

