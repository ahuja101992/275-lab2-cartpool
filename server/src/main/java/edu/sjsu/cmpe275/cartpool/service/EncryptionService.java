package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import org.springframework.stereotype.Service;


@Service
public interface EncryptionService {
    String encrypt(String str) throws Exception;
    String decrypt(String str) throws Exception;
}
