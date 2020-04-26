package edu.sjsu.cmpe275.cartpool.controller;




import edu.sjsu.cmpe275.cartpool.pojos.Product;
import edu.sjsu.cmpe275.cartpool.pojos.ProductId;
import edu.sjsu.cmpe275.cartpool.service.AdminService;
import edu.sjsu.cmpe275.cartpool.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Component
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/product/create",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Product> createStore(@RequestParam Long store_id,
                                        @RequestParam Long sku,
                                        @RequestParam String name,
                                        @RequestParam String desc,
                                        @RequestParam String image_url,
                                        @RequestParam(required = false) String brand,
                                        @RequestParam String unit,
                                        @RequestParam Long price,
                                        @RequestParam Long adminId) {

        adminService.findById(adminId);
        ProductId productId = new ProductId(store_id,sku);
        Product product=null;
        if(brand==null) {
           product = new Product(productId, name, desc, image_url,unit,price);
        }else{
           product = new Product(productId, name, desc, image_url,brand,unit,price);
        }

        Product newProduct = productService.createProduct(product,adminId);
        return newProduct != null ? ResponseEntity.status(HttpStatus.OK).body(product) : null;
    }

    @RequestMapping(value = "/inventory/store/{storeId}/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Set<Product>> deleteStore(@PathVariable Long storeId,
                                                     @PathVariable Long sku,
                                                   @PathVariable Long adminId) {
        Set<Product> products = productService.deleteProduct(storeId, sku,adminId);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @RequestMapping(value = "/inventory/store/{storeId}/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Product> updateStore(@PathVariable Long storeId,
                                                   @PathVariable Long sku,
                                                   @RequestParam String name,
                                                   @RequestParam String desc,
                                                   @RequestParam String image_url,
                                                   @RequestParam String brand,
                                                   @RequestParam String unit,
                                                   @RequestParam Long price,
                                                   @RequestParam Long adminId) {
        adminService.findById(adminId);
        Product product = productService.ffindByStoreId_SKU(storeId,sku);
        if(name!=null){
            product.setName(name);
        }
        if(desc!=null){
            product.setDescription(desc);
        }
        if(image_url!=null){
            product.setImageURL(image_url);
        }
        if(brand!=null){
            product.setBrandName(brand);
        }
        if(unit!=null){
            product.setUnit(unit);
        }
        if(price!=null){
            product.setPrice(price);
        }
        
        return product != null ? ResponseEntity.status(HttpStatus.OK).body(product) : null;
    }



}
