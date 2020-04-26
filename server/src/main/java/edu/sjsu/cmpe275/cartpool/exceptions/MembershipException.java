package edu.sjsu.cmpe275.cartpool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class MembershipException extends RuntimeException {
    public MembershipException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
