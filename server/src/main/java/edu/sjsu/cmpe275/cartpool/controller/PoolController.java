package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.exceptions.MembershipException;
import edu.sjsu.cmpe275.cartpool.pojos.Pool;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.service.PoolService;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
public class PoolController {

    @Autowired
    private PoolService poolService;

    @Autowired
    private PoolerService poolerService;

    @RequestMapping(value = "/pool/test",
            method = RequestMethod.GET)
    public void test() {
        System.out.println("testing");
    }

    @RequestMapping(value = "/pool/create",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Pool> createPool(@RequestParam String poolId,
                                    @RequestParam String name,
                                    @RequestParam String neighborhoodName,
                                    @RequestParam String description,
                                    @RequestParam String zip,
                                    @RequestParam Long poolerId) {

        //// check if pooler creating pool is member of other pool or not /////
        if (!poolService.chceckMembership(poolerId)) {
            //// pooler is already a member of other pool
            throw new MembershipException("pooler is already a member of other pool");
        }

        Pool pool = new Pool.PoolBuilder()
                .poolId(poolId)
                .name(name)
                .neighborhoodName(neighborhoodName)
                .description(description)
                .zip(zip)
                .build();

        Pooler poolLeader = poolerService.findById(poolerId);
        pool.setPoolLeader(poolLeader);
        pool.addPooler(poolLeader);
        poolLeader.setPool(pool);
        return ResponseEntity.status(HttpStatus.OK).body(poolService.save(pool));
    }

    @RequestMapping(value = "/pool/delete/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<Object> deletePool(@PathVariable Long id) {
        poolService.delete(id);
        //return ResponseEntity.status(HttpStatus.OK).body(poolService.delete(id));
        return new ResponseEntity<>("{\"success\": \"Deleted pool successfully\"}", HttpStatus.OK);
    }

    @RequestMapping(value = "/pool/search/{searchParam}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Pool>> searchPool(@PathVariable String searchParam) {
        List<Pool> poolList = poolService.searchPool(searchParam);
        return ResponseEntity.status(HttpStatus.OK).body(poolList);
    }

    @RequestMapping(value = "/pool/join/{poolId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.PUT)
    public @ResponseBody
    void joinPool(@PathVariable Long poolId,
                  @RequestParam Long poolerId,
                  @RequestParam String screenName) {


        poolService.joinPool(poolId, poolerId, screenName);
    }


    @RequestMapping(value = "/pool/verify/{poolerId}/{poolId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Pool> verify(@PathVariable Long poolerId,
                                @PathVariable Long poolId) {

        return ResponseEntity.status(HttpStatus.OK).body(poolService.verify(poolerId, poolId));
    }
}
