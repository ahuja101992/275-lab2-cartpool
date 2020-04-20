package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Pooler;

public interface AdminService {
    public Pooler findById(Long id) throws Exception;
}
