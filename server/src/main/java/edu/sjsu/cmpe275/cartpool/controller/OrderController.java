package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Address;
import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Orders;
import edu.sjsu.cmpe275.cartpool.pojos.Store;
import edu.sjsu.cmpe275.cartpool.pojos.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import edu.sjsu.cmpe275.cartpool.service.OrderService;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import edu.sjsu.cmpe275.cartpool.util.UtilFunctions;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@Autowired
	PoolerService poolerService;
	@RequestMapping(value = "/order/checkout",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<Orders> checkout(@RequestParam(required = false) String deliveryPersonId,
									@RequestParam String orderId,
									@RequestParam long storeId,
									@RequestParam int qty,
									@RequestParam boolean forDelivery,
									@RequestParam String ownerId,		
									@RequestParam long price) {
		long finalPrice= (long) (price+(.0975*price));
		if(forDelivery) 
			poolerService.subtractContribution(ownerId);
		else 
			poolerService.addContribution(ownerId);
        Orders order = new Orders.OrderBuilder()
        		.available(true)
        		.qty(qty)
        		.price(price)
        		.finalPrice(finalPrice)
        		.forDelivery(forDelivery)
        		.status("Placed")
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
	
    @RequestMapping(value = "/ordes/getorderdetails/{id}/",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Orders> verify(@PathVariable long id) {
    	orderService.getOrderDetails(id);
    	return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderDetails(id));
    }

}
