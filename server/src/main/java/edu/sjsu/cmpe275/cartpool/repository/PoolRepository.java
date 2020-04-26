package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Pool;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoolRepository<T> extends CrudRepository<Pool, Long> {

    @Query("SELECT p FROM Pool p WHERE p.name LIKE %?1% OR p.neighborhoodName LIKE %?2% OR p.zip LIKE %?3%" )
    List<Pool> findByNameOrNeighborhoodNameOrZipAllIgnoreCase(String searchParam1, String searchParam2, String searchParam3);
}
