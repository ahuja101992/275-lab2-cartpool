package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Orders;

import java.util.List;


public interface OrderService {
    Orders createOrder(Orders order, String deliveryPersonId, String ownerId, long storeId);

    Orders getOrderDetails(long id);

    List<Orders> getDeliveryOrders(long id);

    List<Orders> getOrdersByOwnerId(long id);

    List<Orders> getOrdersForPickUp(long poolerId, long storeId);

    List<Orders> getAllOrdersForPickup(long id);

    Boolean selectOrders(long poolerId, int count, List<Long> orders);

    void pickUpOrderForDelivery(long deliveryPersonId, long orderId);
    String generateOrderEmail(long id);
    void markOrderDelivered(long orderId);
    Orders markDeliveryNotReceived(long orderId);
    void sendOrderConfirmationEmail(Orders order);
}
