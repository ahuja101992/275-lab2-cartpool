package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Product;
import edu.sjsu.cmpe275.cartpool.pojos.ProductId;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, ProductId> {
    List<Product> findByStoreId(Long storeId);

    @Query(value = "SELECT product.*  FROM product WHERE product.sku= :sku", nativeQuery = true)
    List<Product> findProductsBySku(@Param("sku") String sku);

    List<Product> findByNameAndStoreId(String name,Long storeId);

    List<Product> findByAdmin_Id(Long adminId);

    @Query(value="select p.* from product p ,items i,orders o where i.sku=p.sku and o.id= i.order_id and i.sku= :sku and o.status in (\"placed\")",nativeQuery =true)
    List<Product> findProductsByOrderId(@Param("sku") String sku);

    @Query(value = "SELECT product.*  FROM product GROUP BY product.name", nativeQuery = true)
    List<Product>  findProductsGroupByName();
}
