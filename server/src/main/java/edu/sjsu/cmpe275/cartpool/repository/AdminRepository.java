package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository<T> extends CrudRepository<Admin, Long> {
}
