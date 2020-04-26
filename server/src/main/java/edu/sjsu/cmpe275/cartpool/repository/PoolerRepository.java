package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoolerRepository<T> extends CrudRepository<Pooler, Long> {
    Pooler findByEmail(String email);

    List<Pooler> findByEmailAndPassword(String email, String password);
    List<Pooler> findByEmailAndProvider(String email, String provider_id);
}
