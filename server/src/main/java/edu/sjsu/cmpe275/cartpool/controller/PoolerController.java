package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import edu.sjsu.cmpe275.cartpool.service.PoolerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Get pooler contribution API
     *
     * @param poolerId
     * @return
     */
    @RequestMapping(value = "/pooler/getcontribution/{poolerId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Integer> getContribution(@PathVariable long poolerId) {
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
