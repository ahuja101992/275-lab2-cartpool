package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Pool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoolRepository<T> extends CrudRepository<Pool, Long> {
    List<Pool> findByNameOrNeighborhoodNameOrZip(String searchParam1, String searchParam2, String searchParam3);
}
