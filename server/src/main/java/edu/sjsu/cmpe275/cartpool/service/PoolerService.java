package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Pooler;

public interface PoolerService {
    public Pooler findById(Long id) throws Exception;

    public Pooler login(String email, String password);

    public Pooler save(Pooler pooler);

    public Pooler verify(String email);
}
