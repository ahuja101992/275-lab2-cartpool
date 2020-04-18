package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Address;
import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    PoolerService poolerService;


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

        String domainName = email.split("@")[1];

        if ("sjsu.edu".equals(domainName)) {
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
        return ResponseEntity.status(HttpStatus.OK).body(poolerService.login(email, password));

    }

}
