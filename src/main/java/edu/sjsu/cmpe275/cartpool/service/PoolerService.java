package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.repository.PoolerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PoolerService {
    @Autowired
    PoolerRepository<Pooler> poolerRepository;

    @Transactional
    public Pooler findById(Long id) throws Exception {
        return poolerRepository.findById(id).orElseThrow(() -> new Exception(""));
    }
}
