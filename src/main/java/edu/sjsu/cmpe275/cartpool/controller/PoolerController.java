package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Address;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class PoolerController {
    public class SponsorController {

        @Autowired
        PoolerService poolerService;

        @RequestMapping(value = "/test", method = RequestMethod.GET)
        public @ResponseBody
        String test() {
            return "Hello Test";
        }

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

        @RequestMapping(value = "/sponsor",
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                method = RequestMethod.POST)
        public @ResponseBody
        ResponseEntity<Pooler> createSponsor(@RequestParam String name,
                                             @RequestParam(required = false) String description,
                                             @RequestParam(required = false) String street,
                                             @RequestParam(required = false) String city,
                                             @RequestParam(required = false) String state,
                                             @RequestParam(required = false) String zip) {


            if (name.trim().length() < 2) {
                return new ResponseEntity("Sponsor name too small", HttpStatus.BAD_REQUEST);
            }


            if (street != null) street = street.trim();
            if (city != null) city = city.trim();
            if (state != null) state = state.trim();
            if (zip != null) zip = zip.trim();
            if (name != null) name = name.trim();
            if (description != null) description = description.trim();

            Address address = new Address.AddressBuilder()
                    .street(street)
                    .city(city)
                    .state(state)
                    .zip(zip)
                    .build();

            Pooler sponsor = new Pooler.PoolerBuilder()
                    .firstname(name)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
}
