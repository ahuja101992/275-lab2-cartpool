package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Pool;

import java.util.List;

public interface PoolService {
    Pool save(Pool pool);

    void delete(Long id);

    boolean chceckMembership(Long poolerId);

    List<Pool> searchPool(String searchParam);

    void joinPool(Long poolId, Long poolerId, String screenName);

    Pool verify(Long poolerId, Long poolId);
}
