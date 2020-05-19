package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PoolerRepository<T> extends CrudRepository<Pooler, Long> {
    Optional<Pooler> findByEmail(String email);

    List<Pooler> findByEmailAndPassword(String email, String password);

    List<Pooler> findByEmailAndProvider(String email, String provider_id);

    Pooler findByScreenname(String screenName);

    List<Pooler> findByScreennameOrNicknameOrEmail(String screenname, String nickname, String email);
    Pooler findByNickname(String nickName);
}
