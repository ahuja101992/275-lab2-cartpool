package edu.sjsu.cmpe275.cartpool.service;

public interface EmailService {
    void sendVerificationEmail(String to, String subject, String text);

    void sendDirectMessageViaEmail(String from, String to, String subject, String text);

    public void sendEmailForPoolMembership(String to, String subject, String messageBody);
}
