package edu.sjsu.cmpe275.cartpool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Pool Not Found") //404
public class PoolNotFoundException extends RuntimeException {
    public PoolNotFoundException() {
        super("PoolNotFoundException with provided details");
    }
}
