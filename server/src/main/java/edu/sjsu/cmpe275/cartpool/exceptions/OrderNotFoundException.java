package edu.sjsu.cmpe275.cartpool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Orders Not Found") //404
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("No orders fornd for the order. ");
    }
}
