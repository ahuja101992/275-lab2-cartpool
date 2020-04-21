package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.repository.AdminRepository;
import edu.sjsu.cmpe275.cartpool.repository.PoolerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    @Autowired
    AdminRepository<Pooler> adminRepository;

    @Transactional
    public Admin findById(Long id) throws Exception {
        return adminRepository.findById(id).orElseThrow(() -> new Exception(""));
    }

    @Transactional
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }
}
