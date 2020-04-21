package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Pooler;

public interface PoolerService {
    Pooler findById(Long id) throws Exception;

    Pooler login(String email, String password);

    Pooler save(Pooler pooler);

    Pooler verify(String email);
}
