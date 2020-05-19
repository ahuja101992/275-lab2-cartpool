package edu.sjsu.cmpe275.cartpool.controller;


import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Product;
import edu.sjsu.cmpe275.cartpool.pojos.ProductId;
import edu.sjsu.cmpe275.cartpool.service.AdminService;
import edu.sjsu.cmpe275.cartpool.service.ProductService;
import edu.sjsu.cmpe275.cartpool.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    AdminService adminService;

    @Autowired
    RandomString randomString;

    @RequestMapping(value = "/product/create",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<List<Product>> createStore(@RequestParam List<Long> stores,
                                              @RequestParam String name,
                                              @RequestParam String desc,
                                              @RequestParam String image_url,
                                              @RequestParam(required = false) String brand,
                                              @RequestParam Float qty,
                                              @RequestParam String unit,
                                              @RequestParam Float price,
                                              @RequestParam Long adminId) {

        Admin admin = adminService.findById(adminId);
        List<Product> products = new ArrayList<>();
        String sku = UUID.randomUUID().toString() + name + price;
        for (Long storeId : stores) {
            ProductId productId = new ProductId(storeId, sku);
            Product product = null;
            if (brand == null) {
                product = new Product(productId, name, desc, image_url, unit, Float.valueOf(price), Float.valueOf(qty), admin);
            } else {
                product = new Product(productId, name, desc, image_url, brand, unit, Float.valueOf(price), Float.valueOf(qty), admin);
            }
            Product newProduct = productService.createProduct(product);
            products.add(newProduct);
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @RequestMapping(value = "/product/{sku}/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<List<Product>> deleteProduct(@PathVariable String sku,
                                                       @PathVariable Long adminId) {
        adminService.findById(adminId);
        List<Product> products = productService.deleteProduct(sku, adminId);
        if (products == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(products);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @RequestMapping(value = "/product/{sku}/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},

            method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<List<Product>> updateProduct(@PathVariable String sku,
                                                       @PathVariable Long adminId,
                                                       @RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String desc,
                                                       @RequestParam(required = false) String image_url,
                                                       @RequestParam(required = false) String brand,
                                                       @RequestParam(required = false) String unit,
                                                       @RequestParam(required = false) Float price,
                                                       @RequestParam(required = false) Float qty
    ) {
        adminService.findById(adminId);
        List<Product> products = productService.searchProductBySKU(sku);
        for (Product product : products) {
            if (name != null && !name.isEmpty()) {
                product.setName(name);
            }
            if (desc != null && !desc.isEmpty()) {
                product.setDescription(desc);
            }
            if (image_url != null && !image_url.isEmpty()) {
                product.setImageURL(image_url);
            }
            if (brand != null && !brand.isEmpty()) {
                product.setBrandName(brand);
            }
            if (unit != null && !unit.isEmpty()) {
                product.setUnit(unit);
            }
            if (price != null) {
                product.setPrice(Float.valueOf(price));
            }
            if (qty != null) {
                product.setQty(Float.valueOf(qty));
            }
        }
        productService.updateProduct(products, adminId);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @RequestMapping(value = "/products/groupByName",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Product>> getPorductsGroupByName() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsGroupByName());
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
                                                           @RequestParam String sku) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.ffindByStoreId_SKU(storeId, sku));
    }

    @RequestMapping(value = "/products/name",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Product>> getProductByStoreId_Name(@RequestParam Long storeId,
                                                                  @RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProductByName(name, storeId));
    }

    @RequestMapping(value = "/products/sku/{sku}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Product>> getProductBySKU(@PathVariable String sku) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProductBySKU(sku));
    }

    @RequestMapping(value = "/products/admin/{adminId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Product>> getProducts(@PathVariable Long adminId) {
        adminService.findById(adminId);
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProductsByAdminId(adminId));
    }

}
