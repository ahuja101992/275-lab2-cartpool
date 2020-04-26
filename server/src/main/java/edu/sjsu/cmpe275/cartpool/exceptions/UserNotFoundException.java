package edu.sjsu.cmpe275.cartpool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User Not Found") //404
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("UserNotFoundException with provided details");
    }
}
