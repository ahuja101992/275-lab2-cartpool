package edu.sjsu.cmpe275.cartpool.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;


@Service
public class EncryptionServiceImpl implements EncryptionService {
    String passphrase = "LKW7zR@jZ6pni^3U4x";
    String salt = "kbfkzhqhllvtxfwn";
    Cipher ecipher;
    Cipher dcipher;

    public EncryptionServiceImpl() throws Exception {
        SecretKey secretKey = new SecretKeySpec("Bar12345Bar12345".getBytes(), "AES");
        ecipher = Cipher.getInstance("AES");
        dcipher = Cipher.getInstance("AES");
        ecipher.init(Cipher.ENCRYPT_MODE, secretKey);
        dcipher.init(Cipher.DECRYPT_MODE, secretKey);
    }

    public String encrypt(String str) throws Exception {
        // Encode the string into bytes using utf-8
        byte[] utf8 = str.getBytes("UTF8");

        // Encrypt
        byte[] enc = ecipher.doFinal(utf8);

        // Encode bytes to base64 to get a string
        return new sun.misc.BASE64Encoder().encode(enc);
    }

    public String decrypt(String str) throws Exception {
        // Decode base64 to get bytes
        byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

        byte[] utf8 = dcipher.doFinal(dec);

        // Decode using utf-8
        return new String(utf8, "UTF8");
    }
}
