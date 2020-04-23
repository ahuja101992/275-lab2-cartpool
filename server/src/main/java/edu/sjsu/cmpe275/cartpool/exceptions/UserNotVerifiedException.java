package edu.sjsu.cmpe275.cartpool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Player Not Verified") //406
public class UserNotVerifiedException extends RuntimeException {
    public UserNotVerifiedException() {
        super("UserNotFoundException with provided details");
    }
}
