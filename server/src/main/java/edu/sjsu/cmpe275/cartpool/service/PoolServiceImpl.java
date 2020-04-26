package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.exceptions.MembershipException;
import edu.sjsu.cmpe275.cartpool.exceptions.PoolNotFoundException;
import edu.sjsu.cmpe275.cartpool.pojos.Pool;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.repository.PoolRepository;
import edu.sjsu.cmpe275.cartpool.repository.PoolerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PoolServiceImpl implements PoolService {

    @Autowired
    private PoolRepository<Pool> poolRepository;

    @Autowired
    private PoolerRepository<Pooler> poolerRepository;

    @Transactional
    @Override
    public Pool save(Pool pool) {
        return poolRepository.save(pool);
    }

    @Transactional
    @Override
    public String delete(Long poolId) {
        Pool pool = poolRepository.findById(poolId).orElseThrow(() -> new PoolNotFoundException());

        if(pool.getMembers().size() == 0) {
            poolRepository.delete(pool);
            return "deleted store successfully";
        }

        return "Unable to delete pool!! :: Members exist in pool";
    }

    @Transactional
    @Override
    public boolean chceckMembership(Long poolerId){
        Pooler pooler = poolerRepository.findById(poolerId).orElseThrow(() -> new MembershipException());
        return pooler.getPool() == null;
    }

    @Transactional
    @Override
    public List<Pool> searchPool(String searchParam) {
        poolRepository.findByNameOrNeighborhoodNameOrZip(searchParam, searchParam, searchParam);
        return null;
    }
}
