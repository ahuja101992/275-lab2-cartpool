package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Orders;

import java.util.List;


public interface OrderService {
    Orders createOrder(Orders order, String deliveryPersonId, String ownerId, long storeId);

    Orders getOrderDetails(long id);

    List<Orders> getOrdersByUserId(long id);
    
    List<Orders> getOrdersByOwnerId(long id);
}
