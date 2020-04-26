package edu.sjsu.cmpe275.cartpool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Pooler already exists in other pool")
public class MembershipException extends RuntimeException {
    public MembershipException() {
        super("Pooler already exists in other pool");
    }
}
