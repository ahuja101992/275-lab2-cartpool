package edu.sjsu.cmpe275.cartpool.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sjsu.cmpe275.cartpool.pojos.Cart;
import edu.sjsu.cmpe275.cartpool.pojos.Item;
import edu.sjsu.cmpe275.cartpool.pojos.OrderDetails;
import edu.sjsu.cmpe275.cartpool.pojos.Orders;
import edu.sjsu.cmpe275.cartpool.service.OrderService;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import edu.sjsu.cmpe275.cartpool.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Component
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    PoolerService poolerService;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Method for testing to generate order form postman.
     *
     * @param deliveryPersonId
     * @param storeId
     * @param qty
     * @param forDelivery
     * @param ownerId
     * @param price
     * @return
     */
    @RequestMapping(value = "/order/placeOrder",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Orders> checkout(@RequestParam(required = false) String deliveryPersonId,
                                    @RequestParam long storeId,
                                    @RequestParam int qty,
                                    @RequestParam boolean forDelivery,
                                    @RequestParam String ownerId,
                                    @RequestParam long price) {
        long finalPrice = (long) (price + (.0975 * price));
        if (forDelivery)
            poolerService.subtractContribution(ownerId);
        else
            poolerService.addContribution(ownerId);
        Orders order = new Orders.OrderBuilder()
                .available(true)
                .qty(qty)
                .price(price)
                .finalPrice(finalPrice)
                .forDelivery(forDelivery)
                .status(Constants.PLACED)
                .build();
        Orders result = orderService.createOrder(order, deliveryPersonId, ownerId, storeId);
        orderService.sendOrderConfirmationEmail(result);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /***
     * Picks-up and order for delivery by changing status from PLACED to
     * either "Picked-up by self" or "Picked-up"
     *
     * @param deliveryPersonId - Long id of the deliveryPerson
     * @param orderId - Long id of the order
     * @return
     */
    @RequestMapping(value = "/order/delivery/pickUpOrder",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<List<Orders>> deliveryCheckout(@RequestParam(required = false) long deliveryPersonId,
                                            @RequestParam long orderId) {
        orderService.pickUpOrderForDelivery(deliveryPersonId, orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrdersForPickup(deliveryPersonId));
    }

    /***
     * Completes delivery of an order by changing  status from "Picked-up" to "Delivered"
     *
     * @param orderId - Long id of the order
     * @return
     */
    @RequestMapping(value = "/order/delivery/markDelivered",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<List<Orders>> markDelivered(@RequestParam long orderId, @RequestParam long poolerId) {
        orderService.markOrderDelivered(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getDeliveryOrders(poolerId));
    }

    @RequestMapping(value = "/order/delivery/markDeliveryNotReceived",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<List<Orders>> markDeliveryNotReceived(@RequestParam Long orderId, @RequestParam Long orderOwnerId) {
        orderService.markDeliveryNotReceived(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByOwnerId(orderOwnerId));
    }

    /**
     * to fetch the details of an order
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/order/getOrderDetails/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Orders> getOrderDetails(@PathVariable long id) {
        orderService.getOrderDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderDetails(id));
    }

    /***
     * fetch all the order placed by a pooler 
     * @param id - Long poolerId
     * @return Returns all orders placed by the order owner
     */
    @RequestMapping(value = "/order/getOrdersByOwnerId/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Orders>> getOrdersByOwnerId(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByOwnerId(id));
    }

    /**
     * Fetch all the orders to be delivered by a pooler
     *
     * @param poolerId
     * @return
     */
    @RequestMapping(value = "/order/delivery/getDeliveryOrders/{poolerId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Orders>> getDeliveryOrders(@PathVariable long poolerId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getDeliveryOrders(poolerId));
    }

    /**
     * final submit order method
     *
     * @param cart
     * @return
     */
    @RequestMapping(value = "/order/submitorder",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Orders> submitOrder(@RequestBody Cart cart) {
        long finalPrice = (long) (cart.getPrice() + (.0975 * cart.getPrice()));
        if (cart.getForDelivery())
            poolerService.subtractContribution(cart.getOrderOwner());
        else
            poolerService.addContribution(cart.getOrderOwner());
        Orders order = new Orders.OrderBuilder()
                .available(true)
                .qty(cart.getQty())
                .price(cart.getPrice())
                .finalPrice(finalPrice)
                .forDelivery(cart.getForDelivery())
                .status(Constants.PLACED)
                .build();
        List<Item> itemList = cart.getItems();
        List<OrderDetails> orderList = new ArrayList<>();
        for(Item item : itemList){
            OrderDetails orderDetails=new OrderDetails(item.getQty(),item.getPrice(),item.getSku());
            orderDetails.setOrder(order);
            orderList.add(orderDetails);
        }
        order.setOrderDetails(orderList);
        Orders result = orderService.createOrder(order, cart.getDeliveryBy(), cart.getOrderOwner(), cart.getStore());
        orderService.sendOrderConfirmationEmail(result);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Find all the orders available to pooler for pickup
     *
     * @param poolerId
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/order/getOrdersForPickup/{poolerId}/{storeId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Orders>> getOrdersForPickup(@PathVariable long poolerId, @PathVariable long storeId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersForPickUp(poolerId, storeId));
    }

    /**
     * Fetch all the orders pooler has to pickup including his own - when he is at store
     *
     * @param poolerId
     * @return
     */
    @RequestMapping(value = "/order/delivery/getOrdersForPickup/{poolerId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Orders>> getAllOrdersForPickup(@PathVariable long poolerId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrdersForPickup(poolerId));
    }

    /**
     * post method to update all the orders selected by a pooler for pickup
     *
     * @param poolerId
     * @param count
     * @param orderList
     * @return
     */
    @RequestMapping(value = "/order/selectorders",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Orders> selectOrdersForDelivery(@RequestParam long poolerId,
                                                   @RequestParam int count,
                                                   @RequestBody List<Long> orderList) {
        System.out.println(orderList);
        if (orderList.size() != count) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);/// error to be sent
        if (!orderService.selectOrders(poolerId, count, orderList))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * Service for testing. To be removed.
     *
     * @param poolerId
     * @return
     */
    @RequestMapping(value = "/order/delivery/testService/{poolerId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Orders>> test(@PathVariable long poolerId) {
        System.out.println(orderService.generateOrderEmail(poolerId));
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}
