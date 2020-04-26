package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Pool;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.service.PoolService;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PoolController {

    @Autowired
    private PoolService poolService;

    @Autowired
    private PoolerService poolerService;

    @RequestMapping(value = "/pool/create",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Pool> createPool(@RequestParam String poolId,
                                    @RequestParam String name,
                                    @RequestParam String neighborhoodName,
                                    @RequestParam String description,
                                    @RequestParam String zip,
                                    @RequestParam Long poolerId){

        //// check if pooler creating pool is member of other pool or not /////

        if(poolService.chceckMembership(poolerId)){
            //// pooler is already a member of other pool
            return null;
        }

        Pool pool = new Pool.PoolBuilder()
                .poolId(poolId)
                .name(name)
                .neighborhoodName(neighborhoodName)
                .description(description)
                .zip(zip)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(poolService.save(pool));
    }

    @RequestMapping(value = "/pool/delete",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity<String> deletePool(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(poolService.delete(id));
    }

    @RequestMapping(value = "/pool/join",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<Pool> joinPool(@RequestParam String id,
                                  @RequestParam Pooler pooler){
        //return ResponseEntity.status(HttpStatus.OK).body(poolService.delete(id));
        return null;
    }

}
