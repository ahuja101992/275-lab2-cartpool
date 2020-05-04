package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ProductService {

    Product createProduct(Product product);

    List<Product> deleteProduct(String sku);

    List<Product> updateProduct(List<Product> product);

    Set<Product> searchProductByStoreId(Long storeId);

    List<Product> searchProductBySKU(String sku);

    List<Product> searchProducts();

    Product ffindByStoreId_SKU(Long storeId,String sku);

    List<Product> searchProductByName(String name, Long storeId);

    List<Product> getProductsGroupByName();

}
