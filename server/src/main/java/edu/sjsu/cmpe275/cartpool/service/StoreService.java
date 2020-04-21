package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.pojos.Store;
import edu.sjsu.cmpe275.cartpool.repository.AdminRepository;
import edu.sjsu.cmpe275.cartpool.repository.PoolerRepository;
import edu.sjsu.cmpe275.cartpool.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreService {
    @Autowired
    StoreRepository<Pooler> storeRepository;

    @Autowired
    AdminRepository<Admin> adminRepository;

    @Transactional
    public Store createStore(Store store, Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new UserNotFoundException());
        store.setAdmin(admin);
        return storeRepository.save(store);
    }

    @Transactional
    public Store save(Store store) {
        return storeRepository.save(store);
    }
}
