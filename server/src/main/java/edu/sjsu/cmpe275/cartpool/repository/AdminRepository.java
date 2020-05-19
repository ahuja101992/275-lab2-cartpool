package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository<T> extends CrudRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);

    List<Admin> findByEmailAndPassword(String email, String password);

    List<Admin> findByScreennameOrNicknameOrEmail(String screenname, String nickname, String email);
}
