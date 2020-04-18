package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Address;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PoolerController {
    @Autowired
    PoolerService poolerService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody
    String test() {
        return "Hello Test";
    }
}
