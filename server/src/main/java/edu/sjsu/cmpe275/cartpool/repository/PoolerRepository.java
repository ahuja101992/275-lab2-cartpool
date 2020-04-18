package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PoolerRepository<T> extends CrudRepository<Pooler, Long> {
    List<Pooler> findByEmailAndPassword(String email, String password);
}
