package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Pool;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.service.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PoolController {

    @Autowired
    private PoolService poolService;

    @RequestMapping(value = "/pool/create",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Pool> createPool(@RequestParam String name,
                                    @RequestParam String neighborhoodName,
                                    @RequestParam String description,
                                    @RequestParam String zip){


        Pool pool = new Pool.PoolBuilder()
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
    ResponseEntity<Pool> deletePool(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(poolService.delete(id));
    }

//    @RequestMapping(value = "/pool/delete",
//            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
//            method = RequestMethod.DELETE)
//    public @ResponseBody
//    ResponseEntity<Pool> joinPool(@RequestParam String id,
//                                  @RequestParam Pooler pooler){
//        //return ResponseEntity.status(HttpStatus.OK).body(poolService.delete(id));
//        return null;
//    }


}
