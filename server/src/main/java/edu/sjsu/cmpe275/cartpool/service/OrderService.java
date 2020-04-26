package edu.sjsu.cmpe275.cartpool.service;

import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.cartpool.pojos.Orders;


public interface OrderService {
	Orders createOrder(Orders order, String deliveryPersonId, String ownerId, long storeId);
	Orders getOrderDetails(long id);
}
