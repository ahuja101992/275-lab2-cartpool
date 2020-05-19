package edu.sjsu.cmpe275.cartpool.controller;

import edu.sjsu.cmpe275.cartpool.pojos.Address;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.service.EmailService;
import edu.sjsu.cmpe275.cartpool.service.PoolerService;
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
    PoolerService poolerService;

    @Autowired
    EmailService emailService;

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

    @RequestMapping(value = "/pooler/profile/getById/{poolerId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Pooler> getProfile(@PathVariable long poolerId) {

        return ResponseEntity.status(HttpStatus.OK).body(poolerService.findById(poolerId));
    }

    @RequestMapping(value = "/pooler/messaging/getAll",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Iterable<Pooler>> getAllPoolers() {

        return ResponseEntity.status(HttpStatus.OK).body(poolerService.findAll());
    }

    @RequestMapping(value = "/pooler/messaging/sendMessage",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    void sendMessage(@RequestParam String message,
                     @RequestParam String to,
                     @RequestParam String from) {

        emailService.sendDirectMessageViaEmail(from, to, message);
    }

    @RequestMapping(value = "/pooler/update/{poolerId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<Pooler> updateProfile(@PathVariable long poolerId,
                                         @RequestParam String firstName,
                                         @RequestParam String lastName,
                                         @RequestParam String email,
                                         @RequestParam String imageUrl,
                                         @RequestParam String street,
                                         @RequestParam String city,
                                         @RequestParam String state,
                                         @RequestParam String zip) {

        if (firstName != null) firstName = firstName.trim();
        if (lastName != null) lastName = lastName.trim();
        if (email != null) email = email.trim();
        if (street != null) street = street.trim();
        if (city != null) city = city.trim();
        if (state != null) state = state.trim();
        if (zip != null) zip = zip.trim();

        Address address = new Address.AddressBuilder()
                .street(street)
                .city(city)
                .state(state)
                .zip(zip)
                .build();

        Pooler pooler = poolerService.findById(poolerId);
        pooler.setFirstName(firstName);
        pooler.setLastName(lastName);
        pooler.setEmail(email);
        pooler.setImg(imageUrl);
        pooler.setAddress(address);

        return ResponseEntity.status(HttpStatus.OK).body(poolerService.save(pooler));
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
