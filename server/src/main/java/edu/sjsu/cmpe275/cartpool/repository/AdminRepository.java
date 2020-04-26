package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository<T> extends CrudRepository<Admin, Long> {
    Admin findByEmail(String email);

    List<Admin> findByEmailAndPassword(String email, String password);
}
