package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Pool;
import org.springframework.stereotype.Repository;

@Repository
public interface PoolRepository {

    void save(Pool pool);
}
