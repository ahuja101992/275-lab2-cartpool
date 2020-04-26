package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Product;
import edu.sjsu.cmpe275.cartpool.pojos.ProductId;
import edu.sjsu.cmpe275.cartpool.pojos.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository<T> extends CrudRepository<Product, ProductId> {
    List<Product> findByStoreId(Long storeId);
    List<Product> findBySKU(Long sku);
    List<Product> findByName(String name);
    Optional<T> findByStoreId_SKU(Long storeId, Long sku);
}
