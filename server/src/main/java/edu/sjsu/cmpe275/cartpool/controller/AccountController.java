package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import edu.sjsu.cmpe275.cartpool.util.UtilFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Component
@RestController
public class AccountController {
    @Autowired
    PoolerService poolerService;

    public String test() {
        return "Hello Test";
    }

    @RequestMapping(value = "/ok", method = RequestMethod.GET)
    public @ResponseBody
    String ok() {
        System.out.println("Inside ok");
        return test();
    }

    @RequestMapping(value = "/account/signup",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Pooler> signUp(@RequestParam String screenname,
                                  @RequestParam String nickname,
                                  @RequestParam String email,
                                  @RequestParam(required = false) String password) {

        if (screenname != null) screenname = screenname.trim();
        if (nickname != null) nickname = nickname.trim();
        if (email != null) email = email.trim();

        if (UtilFunctions.isAdmin(email)) {
            //Create admin
            Admin admin = new Admin.AdminBuilder()
                    .screenname(screenname)
                    .nickname(nickname)
                    .email(email)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            //Create pooler
            Pooler pooler = new Pooler.PoolerBuilder()
                    .screenname(screenname)
                    .nickname(nickname)
                    .email(email)
                    .password(password)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(poolerService.save(pooler));
        }
    }

    @RequestMapping(value = "/account/login",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Pooler> login(@RequestParam String email,
                                 @RequestParam(required = false) String password) {

        if (UtilFunctions.isAdmin(email)) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(poolerService.login(email, password));
        }

    }

    @RequestMapping(value = "/account/verify/{email}/",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Pooler> verify(@PathVariable String email) {
        System.out.println("Verify");

        try {
            System.out.println("email: " + email);
            if (email != null) email = URLDecoder.decode(email, "UTF-8");
            System.out.println("email: " + email);
            if (UtilFunctions.isAdmin(email)) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(poolerService.verify(email));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }


    }

}
