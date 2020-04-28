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
        ProductId productId = new ProductId(store_id, sku);
        Product product = null;
        if (brand == null) {
            product = new Product(productId, name, desc, image_url, unit, price);
        } else {
            product = new Product(productId, name, desc, image_url, brand, unit, price);
        }

        Product newProduct = productService.createProduct(product);
        return newProduct != null ? ResponseEntity.status(HttpStatus.OK).body(product) : null;
    }

    @RequestMapping(value = "/product/{storeId}/{sku}/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Set<Product>> deleteProduct(@PathVariable Long storeId,
                                                      @PathVariable Long sku,
                                                      @PathVariable Long adminId) {
        adminService.findById(adminId);
        Set<Product> products = productService.deleteProduct(storeId, sku);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @RequestMapping(value = "/product/{storeId}/{sku}/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},

            method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Product> updateProduct(@PathVariable Long storeId,
                                                 @PathVariable Long sku,
                                                 @PathVariable Long adminId,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String desc,
                                                 @RequestParam(required = false) String image_url,
                                                 @RequestParam(required = false) String brand,
                                                 @RequestParam(required = false) String unit,
                                                 @RequestParam(required = false) Long price
    ) {
        adminService.findById(adminId);
        Product product = productService.ffindByStoreId_SKU(storeId, sku);
        if (name != null) {
            product.setName(name);
        }
        if (desc != null) {
            product.setDescription(desc);
        }
        if (image_url != null) {
            product.setImageURL(image_url);
        }
        if (brand != null) {
            product.setBrandName(brand);
        }
        if (unit != null) {
            product.setUnit(unit);
        }
        if (price != null) {
            product.setPrice(price);
        }

        productService.updateProduct(product);
        return product != null ? ResponseEntity.status(HttpStatus.OK).body(product) : null;
    }

    @RequestMapping(value = "/products/{storeId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Set<Product>> getStoreByStoreId(@PathVariable Long storeId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProductByStoreId(storeId));
    }

    @RequestMapping(value = "/products/sku",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Product> getProductByStoreId_SKU(@RequestParam Long storeId,
                                                           @RequestParam Long sku) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.ffindByStoreId_SKU(storeId, sku));
    }

    @RequestMapping(value = "/products/name",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Product>> getProductByStoreId_SKU(@RequestParam Long storeId,
                                                                 @RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProductByName(name, storeId));
    }

    //    @RequestMapping(value = "/products/sku/{sku}",
//            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
//            method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<List<Product>> getProductBySKU(@PathVariable Long sku) {
//        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProductBySKU(sku));
//    }

}
