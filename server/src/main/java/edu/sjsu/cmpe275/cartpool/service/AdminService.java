package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import org.springframework.stereotype.Service;


@Service
public interface AdminService {
    Admin findById(Long id);

    Admin save(Admin admin);
}
