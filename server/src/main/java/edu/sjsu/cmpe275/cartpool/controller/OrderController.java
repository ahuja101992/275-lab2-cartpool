package edu.sjsu.cmpe275.cartpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.cartpool.service.OrderService;

@Component
@RestController
public class OrderController {
	@Autowired
	OrderService orderService;

}
