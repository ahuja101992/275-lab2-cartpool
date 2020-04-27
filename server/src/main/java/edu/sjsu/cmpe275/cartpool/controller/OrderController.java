package edu.sjsu.cmpe275.cartpool.controller;

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

import java.util.List;

@Component
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    PoolerService poolerService;

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
        orderService.createOrder(order, deliveryPersonId, ownerId, storeId);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/order/delivery/checkout",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Orders> deliveryCheckout(@RequestParam(required = false) String deliveryPersonId,
                                            @RequestParam String orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/order/delivery/markDelivered",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Orders> markDelivered(@RequestParam String orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/order/delivery/markDeliveryNotReceived",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Orders> markDeliveryNotReceived(@RequestParam String orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/order/getOrderDetails/{id}/",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Orders> verify(@PathVariable long id) {
        orderService.getOrderDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderDetails(id));
    }

    /***
     *
     * @param id - Long poolerId
     * @return Returns all orders placed by the user
     */
    @RequestMapping(value = "/order/getOrdersByUserId/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Orders>> getOrdersByUserId(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByUserId(id));
    }

}
