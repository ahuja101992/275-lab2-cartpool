package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Store;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoreRepository<T> extends CrudRepository<Store, Long> {
    List<Store> findByIdAndAdmin_id(Long email, Long password);
}
