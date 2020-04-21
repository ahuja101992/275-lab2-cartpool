package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.pojos.User;
import edu.sjsu.cmpe275.cartpool.service.AdminService;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import edu.sjsu.cmpe275.cartpool.service.PoolerServiceImpl;
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
    PoolerServiceImpl poolerServiceImpl;

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/account/signup",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<User> signUp(@RequestParam String screenName,
                                @RequestParam String nickName,
                                @RequestParam String email,
                                @RequestParam(required = false) String password) {

        if (screenName != null) screenName = screenName.trim();
        if (nickName != null) nickName = nickName.trim();
        if (email != null) email = email.trim();

        if (UtilFunctions.isAdmin(email)) {
            //Create admin
            Admin admin = new Admin.Builder()
                    .screenname(screenName)
                    .nickname(nickName)
                    .email(email)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(adminService.save(admin));
        } else {
            //Create pooler
            Pooler pooler = new Pooler.Builder()
                    .screenname(screenName)
                    .nickname(nickName)
                    .email(email)
                    .password(password)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(poolerServiceImpl.save(pooler));
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
            return ResponseEntity.status(HttpStatus.OK).body(poolerServiceImpl.login(email, password));
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
                return ResponseEntity.status(HttpStatus.OK).body(poolerServiceImpl.verify(email));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

}
