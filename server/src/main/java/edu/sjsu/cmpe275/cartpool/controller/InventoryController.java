package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.*;
import edu.sjsu.cmpe275.cartpool.service.AdminService;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import edu.sjsu.cmpe275.cartpool.service.StoreService;
import edu.sjsu.cmpe275.cartpool.util.UtilFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


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
import java.util.List;

@Component
@RestController
public class InventoryController {
    @Autowired
    StoreService storeService;

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/inventory/store",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Store> createStore(@RequestParam String name,
                                       @RequestParam String street,
                                       @RequestParam String city,
                                       @RequestParam String state,
                                       @RequestParam String zip,
                                      @RequestParam Long adminId) {
        Address address = new Address.AddressBuilder()
                .street(street)
                .city(city)
                .state(state)
                .zip(zip)
                .build();

        Store store = new Store.StoreBuilder()
                .name(name)
                .address(address)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(storeService.createStore(store, adminId));
    }

    @RequestMapping(value = "/inventory/getByAdmin/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Store>> getSponsor(@PathVariable Long adminId) {
        Admin admin = adminService.findById(adminId);
        return admin != null ? ResponseEntity.status(HttpStatus.OK).body(admin.getStores())  : null;
    }
}