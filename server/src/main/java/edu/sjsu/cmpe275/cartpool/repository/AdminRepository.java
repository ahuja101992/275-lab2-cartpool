package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository<T> extends CrudRepository<Pooler, Long> {
}
