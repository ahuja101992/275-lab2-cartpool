package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Pool;

public interface PoolService {
    Pool save(Pool pool);

    Pool delete(String id);
}
