package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Pool;

import java.util.List;

public interface PoolService {
    Pool save(Pool pool);

    String delete(Long id);

    boolean chceckMembership(Long poolerId);

    List<Pool> searchPool(String searchParam);

    String joinPool(Long poolId, Long poolerId, String screenName);

    Pool verify(Long poolerId, Long poolId);

    void reject(Long poolId, Long poolerId);

    Long getLeader(Long poolId);

    Pool updatePool(Long poolId, String name,String neighborhoodName, String description);
    public void rejectByPooler(Long poolerId, Long poolId);
    public void verifyByPooler(Long poolerId, Long poolId);
}
