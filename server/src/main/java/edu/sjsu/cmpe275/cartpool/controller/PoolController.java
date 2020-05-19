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
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
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
    ResponseEntity<Object> createPool(@RequestParam String poolId,
                                    @RequestParam String name,
                                    @RequestParam String neighborhoodName,
                                    @RequestParam String description,
                                    @RequestParam String zip,
                                    @RequestParam Long poolerId) {

        //// check if pooler creating pool is member of other pool or not /////
        if (!poolService.chceckMembership(poolerId)) {
            //// pooler is already a member of other pool
            return new ResponseEntity<>("{\"message\": \"you are already a member of other pool!!\"}", HttpStatus.OK);
        }

        if(!poolService.findPoolByName(name)){
            return new ResponseEntity<>("{\"message\": \"Pool with same pool name already exists!!\"}", HttpStatus.CONFLICT);
        }
        if(!poolService.findPoolByPoolId(poolId)){
            return new ResponseEntity<>("{\"message\": \"Pool with same PoolId already exists!!\"}", HttpStatus.CONFLICT);
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

        try{
            poolService.save(pool);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>("{\"message\": \"zip code should be 5 digit valid area code!!\"}", HttpStatus.NOT_ACCEPTABLE);
        }catch(Exception e){
            return new ResponseEntity<>("{\"message\": \"Server not responding!!!\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("{\"message\": \"created pool successfully!!\" , \"id\":" + pool.getId() + "}", HttpStatus.OK);
    }

    @RequestMapping(value = "/pool/delete/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<Object> deletePool(@PathVariable Long id) {
        //poolService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(poolService.delete(id));
        //return new ResponseEntity<>("{\"message\": \"Deleted pool successfully\"}", HttpStatus.OK);
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
    ResponseEntity<Object> joinPool(@PathVariable Long poolId,
                  @RequestParam Long poolerId,
                  @RequestParam String screenName) {


        if (!poolService.chceckMembership(poolerId)) {
            //// pooler is already a member of other pool
            return new ResponseEntity<>("{\"message\": \"you are already a member of other pool\"}", HttpStatus.OK);
        }
        return new ResponseEntity<>( poolService.joinPool(poolId, poolerId, screenName), HttpStatus.OK);
    }


    @RequestMapping(value = "/pool/verify/byPoolLeader/{poolerId}/{poolId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Object> verifyByPoolLeader(@PathVariable Long poolerId,
                                @PathVariable Long poolId) {

        if (!poolService.chceckMembership(poolerId)) {
            //// pooler is already a member of other pool
            return new ResponseEntity<>("{\"message\": \"you are already a member of other pool\"}", HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"message\": \"successfully joined the pool\"}", HttpStatus.OK);
        //return ResponseEntity.status(HttpStatus.OK).body(poolService.verify(poolerId, poolId));
    }

    @RequestMapping(value = "/pool/reject/byPoolLeader/{poolerId}/{poolId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
   void rejectByPoolLeader(@PathVariable Long poolerId,
                                @PathVariable Long poolId) {
        poolService.reject(poolerId, poolId);

        //return new ResponseEntity<>(poolService.reject(poolerId, poolId), HttpStatus.OK);
    }

    @RequestMapping(value = "/pool/support/byPooler/{poolerId}/{poolId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    void verifyByPooler(@PathVariable Long poolerId,
                                @PathVariable Long poolId) {

        poolService.verifyByPooler(poolerId, poolId);
        //return ResponseEntity.status(HttpStatus.OK).body(poolService.verifyByPooler(poolerId, poolId));
    }

    @RequestMapping(value = "/pool/reject/byPooler/{poolerId}/{poolId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    void rejectByPooler(@PathVariable Long poolerId,
                @PathVariable Long poolId) {
        poolService.rejectByPooler(poolerId, poolId);

        //return new ResponseEntity<>(poolService.reject(poolerId, poolId), HttpStatus.OK);
    }

    @RequestMapping(value = "/pool/getLeader/{poolId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Long> getLeader(@PathVariable Long poolId) {
        return new ResponseEntity<>(poolService.getLeader(poolId), HttpStatus.OK);
    }

    @RequestMapping(value = "/pool/update/{poolId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<Pool> updatePool(@PathVariable Long poolId,
                                    @RequestParam String name,
                                    @RequestParam String neighborhoodName,
                                    @RequestParam String description) {

        return ResponseEntity.status(HttpStatus.OK).body(poolService.updatePool(poolId, name, neighborhoodName, description));
    }
}
