package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Product;


import java.util.Set;

public interface ProductService {

    Product createProduct(Product product, Long adminId);

    Set<Product> deleteProduct(Long storeId,Long productId, Long adminId);

    Set<Product> updateProduct(Product product, Long adminId);

    Set<Product> searchProductByStoreId(Long storeId);

}
