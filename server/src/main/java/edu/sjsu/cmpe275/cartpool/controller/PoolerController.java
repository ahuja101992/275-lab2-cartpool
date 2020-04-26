package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Orders;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.pojos.Store;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import edu.sjsu.cmpe275.cartpool.service.PoolerServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class PoolerController {
    @Autowired
    PoolerServiceImpl poolerServiceImpl;
    @Autowired
    PoolerService poolerService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody
    String test() {
        return "Hello Test";
    }
    
    @RequestMapping(value = "/pooler/getcontribution",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<Integer> getContribution(@RequestParam String poolerId) {
    	int contribution = poolerService.getContribution(poolerId);
    	
    	return ResponseEntity.status(HttpStatus.OK).body(contribution);
    }
    
//    @RequestMapping(value = "/inventory/store/getByAdmin/{adminId}",
//            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
//            method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<List<Store>> getStoreByAdmin(@PathVariable Long adminId) {
//        Admin admin = adminService.findById(adminId);
//        return admin != null ? ResponseEntity.status(HttpStatus.OK).body(admin.getStores()) : null;
//    }

    
}
