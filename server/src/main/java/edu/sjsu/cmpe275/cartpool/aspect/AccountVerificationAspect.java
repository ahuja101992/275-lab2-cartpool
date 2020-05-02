package edu.sjsu.cmpe275.cartpool.aspect;

import edu.sjsu.cmpe275.cartpool.service.EmailService;
import edu.sjsu.cmpe275.cartpool.util.Constants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

@Component
@Aspect
public class AccountVerificationAspect {
    @Autowired
    public EmailService emailService;

    @AfterReturning(pointcut = "execution(public * edu.sjsu.cmpe275.cartpool.controller.AccountController.signUp(..))", returning = "result")
    public void sendVerificationEmail(JoinPoint joinPoint, Object result) {
        //email - cartpool.noreply@gmail.com
        //login password - LKW7zR@jZ6pni^3U4x
        //app password - kbfkzhqhllvtxfwn
        Object[] args = joinPoint.getArgs();
        String email = (String) args[2];

        System.out.println(result);
        System.out.println(result.getClass());
        System.out.println("[AccountVerificationAspect] sendVerificationEmail =" + Arrays.toString(joinPoint.getArgs()));

        System.out.println("Inside sendVerificationEmail");

        URI uri;
        URL url = null;
        try {
            String protocol = "http";
            String host = Constants.HOSTNAME;
            int port = 3000;
            String path = "/login/" + email;
            uri = new URI(protocol, null, host, port, path, null, null);
            url = uri.toURL();
        } catch (Exception e) {
            e.printStackTrace();
        }

        emailService.sendVerificationEmail(email,
                "CartPoll account verification ",
                String.format("Please verify your account by" +
                        " clicking on the following link - %s", url));
    }

    public void checkAccountIsVerified(JoinPoint joinPoint) {

    }
}
