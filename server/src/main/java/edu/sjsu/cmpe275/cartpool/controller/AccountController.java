package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Address;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class AccountController {

    @Autowired
    PoolerService poolerService;

    @RequestMapping(value = "/sponsor/{name}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public Pooler getSponsor(@PathVariable Long id) throws Exception {
        Pooler pooler = poolerService.findById(id);
        if (pooler == null) {
            //throw new SponsorNotFoundException(name.trim());
        }

        return pooler;
    }

    @RequestMapping(value = "/signup",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Pooler> createSponsor(@RequestParam String screenname,
                                         @RequestParam String nickname,
                                         @RequestParam String email) {

        if (screenname != null) screenname = screenname.trim();
        if (nickname != null) nickname = nickname.trim();
        if (email != null) email = email.trim();

        String domainName = email.split("@")[1];

        if ("sjsu.edu".equals(domainName)) {
            //Create admin
        } else {
            //Create pooler
            Pooler pooler = new Pooler.PoolerBuilder()
                    .screenname(screenname)
                    .nickname(nickname)
                    .email(email)
                    .build();
        }



        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
