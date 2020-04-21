package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.exceptions.StoreNotFoundException;
import edu.sjsu.cmpe275.cartpool.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.pojos.Store;
import edu.sjsu.cmpe275.cartpool.repository.AdminRepository;
import edu.sjsu.cmpe275.cartpool.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
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
    public List<Store> deleteStore(Long storeId, Long adminId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException());
        System.out.println("Deleting store: " + store);
        storeRepository.delete(store);
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new UserNotFoundException());
        return admin != null ? admin.getStores() : null;
    }

    @Transactional
    public List<Store> updateStore(Store store, Long adminId) {
        storeRepository.save(store);
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new UserNotFoundException());
        return admin != null ? admin.getStores() : null;
    }

    @Transactional
    public Store findStore(Long storeId, Long adminId) {
        List<Store> result = storeRepository.findByIdAndAdmin_id(storeId, adminId);
        if (result.size() >= 1) {
            return result.get(0);
        }

        throw new UserNotFoundException();
    }
}
