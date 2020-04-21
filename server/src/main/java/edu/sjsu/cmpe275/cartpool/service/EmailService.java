package edu.sjsu.cmpe275.cartpool.service;

public interface EmailService {
    public void sendVerificationEmail(String to, String subject, String text);
    public void sendDirectMessageViaEmail(String from, String to, String subject, String text);
}
