package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ProductService {

    Product createProduct(Product product);

    Set<Product> deleteProduct(Long storeId, Long sku);

    Set<Product> updateProduct(Product product);

    Set<Product> searchProductByStoreId(Long storeId);

    Product ffindByStoreId_SKU(Long storeId, Long sku);

    List<Product> searchProductBySKU(Long sku);

    List<Product> searchProductByName(String name, Long storeId);

}
