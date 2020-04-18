package edu.sjsu.cmpe275.cartpool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Sponsor Not Found") //404
public class SponsorNotFoundException extends RuntimeException {
    public SponsorNotFoundException(String name) {
        super("SponsorNotFoundException with name=" + name);
    }
}


