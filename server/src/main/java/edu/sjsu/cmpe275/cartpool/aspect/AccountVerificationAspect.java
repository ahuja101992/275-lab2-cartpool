package edu.sjsu.cmpe275.cartpool.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AccountVerificationAspect {
    //@AfterReturning("execution(public void edu.sjsu.cmpe275")
    public void sendVerificationEmail(JoinPoint joinPoint) {

    }

    public void checkAccountIsVerified(JoinPoint joinPoint) {

    }
}
