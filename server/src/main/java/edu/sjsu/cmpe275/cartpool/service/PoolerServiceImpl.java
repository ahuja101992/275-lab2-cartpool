package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.cartpool.exceptions.UserNotVerifiedException;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.repository.PoolerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PoolerServiceImpl implements PoolerService {
    @Autowired
    PoolerRepository<Pooler> poolerRepository;


    @Transactional
    public Pooler findById(Long id) {
        return poolerRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    @Transactional
    public Pooler login(String email, String password) {
        List<Pooler> result = poolerRepository.findByEmailAndPassword(email, password);
        if (result.size() >= 1) {
            return result.get(0);
        }
        throw new UserNotFoundException();
    }

    @Transactional
    public Pooler loginOAuth(String email, String provider_id) {

        List<Pooler> result = poolerRepository.findByEmailAndProvider(email, provider_id);
        if (result.size() >= 1) {
            if (!result.get(0).getIs_verified())
                throw new UserNotVerifiedException();
            return result.get(0);
        }
        throw new UserNotFoundException();///
    }

    @Transactional
    public Pooler save(Pooler pooler) {
        return poolerRepository.save(pooler);
    }

    @Transactional
    public Pooler verify(String email) {
        Pooler pooler = poolerRepository.findByEmail(email);
        pooler.setIs_verified(true);

        return poolerRepository.save(pooler);
    }

    public void addContribution(String email) {
        Pooler users = poolerRepository.findByEmail(email);
        if (users != null)
            users.setContribution(users.getContribution() + 1);
        else
            throw new UserNotFoundException();
    }

    public void subtractContribution(String email) {
        Pooler users = poolerRepository.findByEmail(email);
        if (users != null)
            users.setContribution(users.getContribution() - 1);
        else
            throw new UserNotFoundException();
    }

    ////// need to check this service
    @Override
    public int getContribution(long id) {
        int contribution = 0;
        Pooler users = poolerRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        if (users != null) {
            contribution = users.getContribution();
        } else {
            throw new UserNotFoundException();
        }
        return contribution;
    }

    public Iterable<Pooler> findAll() {
        return poolerRepository.findAll();
    }
}
