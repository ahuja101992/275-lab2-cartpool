package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Product;
import org.springframework.stereotype.Service;


import java.util.Set;

@Service
public interface ProductService {

    Product createProduct(Product product, Long adminId);

    Set<Product> deleteProduct(Long storeId,Long sku, Long adminId);

    Set<Product> updateProduct(Product product, Long adminId);

    Set<Product> searchProductByStoreId(Long storeId);

//    Product ffindByStoreId_SKU(Long storeId, Long sku);

}
