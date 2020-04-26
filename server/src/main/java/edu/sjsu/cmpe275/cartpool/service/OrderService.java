package edu.sjsu.cmpe275.cartpool.service;

import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.cartpool.pojos.Orders;

import java.util.List;
import java.util.Set;


public interface OrderService {
	Orders createOrder(Orders order, String deliveryPersonId, String ownerId, long storeId);
	Orders getOrderDetails(long id);
	List<Orders> getOrdersByUserId(long id);
}
