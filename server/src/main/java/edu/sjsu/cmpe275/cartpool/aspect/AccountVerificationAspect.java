package edu.sjsu.cmpe275.cartpool.aspect;

import edu.sjsu.cmpe275.cartpool.mail.EmailServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class AccountVerificationAspect {
    @Autowired
    public EmailServiceImpl emailServiceImpl;

    @AfterReturning("execution(public * edu.sjsu.cmpe275.cartpool.controller.AccountController.signUp(..))")
    public void sendVerificationEmail(JoinPoint joinPoint) {
        //email - cartpool.noreply@gmail.com
        //login password - LKW7zR@jZ6pni^3U4x
        //app password - kbfkzhqhllvtxfwn
        System.out.println("[AccountVerificationAspect] sendVerificationEmail =" + Arrays.toString(joinPoint.getArgs()));

        System.out.println("Inside sendVerificationEmail");

        emailServiceImpl.sendSimpleMessage("vijendersingh.aswal@sjsu.edu", "test", "test body");
    }

    public void checkAccountIsVerified(JoinPoint joinPoint) {

    }
}
