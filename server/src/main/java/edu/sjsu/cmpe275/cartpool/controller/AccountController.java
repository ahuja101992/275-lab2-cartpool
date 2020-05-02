package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Address;
import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.pojos.User;
import edu.sjsu.cmpe275.cartpool.service.AdminService;
import edu.sjsu.cmpe275.cartpool.service.EncryptionService;
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

    @Autowired
    AdminService adminService;

    @Autowired
    EncryptionService encryptionService;

    @RequestMapping(value = "/account/signup",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<User> signUp(@RequestParam String screenName,
                                @RequestParam String nickName,
                                @RequestParam String email,
                                @RequestParam(required = false) String street,
                                @RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName,
                                @RequestParam(required = false) String city,
                                @RequestParam(required = false) String state,
                                @RequestParam(required = false) String zip,
                                @RequestParam(required = false) String password,
                                @RequestParam(required = false) String accessToken,
                                @RequestParam(required = false) String provider,
                                @RequestParam(required = false) String provider_id) throws Exception {
    
        if (screenName != null) screenName = screenName.trim();
        if (nickName != null) nickName = nickName.trim();
        if (email != null) email = email.trim();
        if (firstName != null) firstName = firstName.trim();
        if (lastName != null) lastName = lastName.trim();

        Address address = new Address.AddressBuilder()
                .street(street)
                .city(city)
                .state(state)
                .zip(zip)
                .build();

        if (UtilFunctions.isAdmin(email)) {
            //Create admin
            Admin admin = new Admin.Builder()
                    .screenname(screenName)
                    .nickname(nickName)
                    .email(email)
                    .password(encryptionService.encrypt(password))
                    .address(address)
                    .firstName(firstName)
                    .lastName(lastName)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(adminService.save(admin));
        } else {
            //Create pooler
            Pooler pooler = null;
            if (provider != "" && provider != null) {
                pooler = new Pooler.Builder()
                        .screenname(screenName)
                        .nickname(nickName)
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .accessToken(accessToken)
                        .provider(provider)
                        .provider_id(provider_id)
                        .address(address)
                        .build();
            } else {
                pooler = new Pooler.Builder()
                        .screenname(screenName)
                        .nickname(nickName)
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .password(encryptionService.encrypt(password))
                        .address(address)
                        .build();
            }

            return ResponseEntity.status(HttpStatus.OK).body(poolerService.save(pooler));
        }
    }

    @RequestMapping(value = "/account/login",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<User> login(@RequestParam String email,
                               @RequestParam(required = false) String password,
                               @RequestParam(required = false) String provider_id,
                               @RequestParam(required = false) String provider) throws Exception {

        if (UtilFunctions.isAdmin(email)) {
            return ResponseEntity.status(HttpStatus.OK).body(adminService.login(email, encryptionService.encrypt(password)));
        } else {
            if (provider != null && provider != "")
                return ResponseEntity.status(HttpStatus.OK).body(poolerService.loginOAuth(email, provider));
            else
                return ResponseEntity.status(HttpStatus.OK).body(poolerService.login(email, encryptionService.encrypt(password)));
        }

    }

    @RequestMapping(value = "/account/verify/{email}/",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<User> verify(@PathVariable String email) {
        System.out.println("Verify");

        try {
            System.out.println("email: " + email);
            if (email != null) email = URLDecoder.decode(email, "UTF-8");
            System.out.println("email: " + email);
            if (UtilFunctions.isAdmin(email)) {
                return ResponseEntity.status(HttpStatus.OK).body(adminService.verify(email));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(poolerService.verify(email));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

}
