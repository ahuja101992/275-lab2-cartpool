package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.exceptions.StoreNotFoundException;
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
    ResponseEntity<List<Store>> createStore(@RequestParam String name,
                                            @RequestParam String street,
                                            @RequestParam String city,
                                            @RequestParam String state,
                                            @RequestParam String zip,
                                            @RequestParam(required = false) Long storeId,
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

        System.out.println("storeId class: " + storeId.getClass());
        if (storeId != null) store.setId(storeId.longValue());

        storeService.createStore(store, adminId);

        Admin admin = adminService.findById(adminId);
        return admin != null ? ResponseEntity.status(HttpStatus.OK).body(admin.getStores())  : null;
    }

    @RequestMapping(value = "/inventory/store/getByAdmin/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Store>> getStoreByAdmin(@PathVariable Long adminId) {
        Admin admin = adminService.findById(adminId);
        return admin != null ? ResponseEntity.status(HttpStatus.OK).body(admin.getStores())  : null;
    }

    @RequestMapping(value = "/inventory/store/{storeId}/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<List<Store>> deleteStore(@PathVariable Long storeId,
                                                   @PathVariable Long adminId) {
        List<Store> stores = storeService.deleteStore(storeId, adminId);
        System.out.println(stores.size());
        System.out.println(stores);
        return ResponseEntity.status(HttpStatus.OK).body(stores);
    }

    @RequestMapping(value = "/inventory/store/{storeId}/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<List<Store>> updateStore(@PathVariable Long storeId,
                                                   @PathVariable Long adminId,
                                                   @RequestParam String name,
                                                   @RequestParam String street,
                                                   @RequestParam String city,
                                                   @RequestParam String state,
                                                   @RequestParam String zip) {
        Store store = storeService.findStore(storeId, adminId);
        if (store != null) {
            Address address = new Address.AddressBuilder()
                    .street(street)
                    .city(city)
                    .state(state)
                    .zip(zip)
                    .build();

            store.setAddress(address);
            store.setName(name);
        }

        return ResponseEntity.status(HttpStatus.OK).body(storeService.updateStore(store, adminId));
    }




}
