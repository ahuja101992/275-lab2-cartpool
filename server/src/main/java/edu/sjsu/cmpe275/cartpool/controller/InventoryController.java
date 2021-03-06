package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Address;
import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Orders;
import edu.sjsu.cmpe275.cartpool.pojos.Store;
import edu.sjsu.cmpe275.cartpool.service.AdminService;
import edu.sjsu.cmpe275.cartpool.service.OrderService;
import edu.sjsu.cmpe275.cartpool.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
public class InventoryController {
    @Autowired
    StoreService storeService;

    @Autowired
    AdminService adminService;

    @Autowired
    OrderService orderService;

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

        if (storeId != null) store.setId(storeId.longValue());

        if (storeId == null && storeService.findByName(name) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        storeService.createStore(store, adminId);

        Admin admin = adminService.findById(adminId);
        return admin != null ? ResponseEntity.status(HttpStatus.OK).body(admin.getStores()) : null;
    }

    @RequestMapping(value = "/inventory/store/getByAdmin/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Store>> getStoreByAdmin(@PathVariable Long adminId) {
        Admin admin = adminService.findById(adminId);
        return admin != null ? ResponseEntity.status(HttpStatus.OK).body(admin.getStores()) : null;
    }

    @RequestMapping(value = "/inventory/store/{storeId}/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<List<Store>> deleteStore(@PathVariable Long storeId,
                                                   @PathVariable Long adminId) {
        try {
            List<Orders> orders = orderService.getActiveOrders(storeId);
            if (orders.size() > 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

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

    @RequestMapping(value = "/inventory/store/allstores",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public ResponseEntity<List<Store>> getAllStores() {
        return ResponseEntity.status(HttpStatus.OK).body(storeService.getAllStores());
    }

    @RequestMapping(value = "/inventory/store/getactiveorders/{storeId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Orders>> getActiveOrders(@PathVariable Long storeId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getActiveOrders(storeId));
    }
}
