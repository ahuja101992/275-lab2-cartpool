package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Pool;
import edu.sjsu.cmpe275.cartpool.repository.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PoolServiceImpl implements PoolService{

    @Autowired
    private PoolRepository poolRepository;

    @Transactional
    @Override
    public void save(Pool pool) {
        poolRepository.save(pool);
    }
}
