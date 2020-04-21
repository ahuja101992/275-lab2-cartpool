package edu.sjsu.cmpe275.cartpool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Store Not Found") //404
public class StoreNotFoundException extends RuntimeException {
    public StoreNotFoundException() {
        super("StoreNotFoundException with provided details");
    }
}
