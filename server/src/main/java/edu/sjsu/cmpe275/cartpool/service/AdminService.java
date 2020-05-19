package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AdminService {
    Admin findById(Long id);

    Admin save(Admin admin);

    Admin login(String email, String password);

    Admin verify(String email);

    List<Admin> findByScreennameOrNicknameOrEmail(String screenname, String nickname, String email);



}
