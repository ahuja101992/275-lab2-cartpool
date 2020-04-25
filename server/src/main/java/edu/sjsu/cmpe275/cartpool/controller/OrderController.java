package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Address;
import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Orders;
import edu.sjsu.cmpe275.cartpool.pojos.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import edu.sjsu.cmpe275.cartpool.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@Component
@RestController
public class OrderController {
	@Autowired
	OrderService orderService;

	@RequestMapping(value = "/order/checkout",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<Orders> checkout(@RequestParam String deliveryPersonId,
									@RequestParam String orderId) {
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@RequestMapping(value = "/order/markDeliveryNotReceived",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<Orders> markDeliveryNotReceived(@RequestParam String orderId) {
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
