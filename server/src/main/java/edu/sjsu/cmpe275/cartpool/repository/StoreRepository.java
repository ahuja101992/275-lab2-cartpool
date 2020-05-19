package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Store;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository<T> extends CrudRepository<Store, Long> {
    List<Store> findByIdAndAdmin_id(Long email, Long password);

    Optional<Store> findById(Long storeId);

    Optional<Store> findByName(String name);

    List<Store> findAll();
}
