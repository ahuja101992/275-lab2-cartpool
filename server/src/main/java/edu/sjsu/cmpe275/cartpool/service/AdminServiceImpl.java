package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository<Admin> adminRepository;

    @Transactional
    public Admin findById(Long id) {
        return adminRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    @Transactional
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Transactional
    public Admin login(String email, String password) {
        List<Admin> result = adminRepository.findByEmailAndPassword(email, password);
        if (result.size() >= 1) {
            return result.get(0);
        }

        throw new UserNotFoundException();
    }

    @Transactional
    public Admin verify(String email) {
        Admin admin = adminRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        admin.setIs_verified(true);
        return adminRepository.save(admin);
    }
}
