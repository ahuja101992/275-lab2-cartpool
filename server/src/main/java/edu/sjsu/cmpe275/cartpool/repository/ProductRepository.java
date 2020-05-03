package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Product;
import edu.sjsu.cmpe275.cartpool.pojos.ProductId;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, ProductId> {
    List<Product> findByStoreId(Long storeId);

    List<Product> findAll(Example<Product> product);

    List<Product> findByNameAndStoreId(String name,Long storeId);

    @Query(value = "SELECT product.*  FROM product GROUP BY product.name", nativeQuery = true)
    List<Product>  findProductsGroupByName();
}
