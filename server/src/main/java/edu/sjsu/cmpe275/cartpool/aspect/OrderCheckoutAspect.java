package edu.sjsu.cmpe275.cartpool.aspect;

import edu.sjsu.cmpe275.cartpool.service.EmailServiceImpl;
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
public class OrderCheckoutAspect {
    @Autowired
    public EmailServiceImpl emailServiceImpl;

    @AfterReturning(pointcut = "execution(public * edu.sjsu.cmpe275.cartpool.controller.OrderController.checkout(..))", returning = "result")
    public void sendCheckoutSuccessEmail(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();

        System.out.println(result);
        System.out.println(result.getClass());
        System.out.println("[OrderCheckoutAspect] sendCheckoutSuccessEmail =" + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(public * edu.sjsu.cmpe275.cartpool.controller.OrderController.checkout(..))", returning = "result")
    public void sendDeliveryInstructionEmail(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();

        System.out.println(result);
        System.out.println(result.getClass());
        System.out.println("[OrderCheckoutAspect] sendDeliveryInstructionEmail =" + Arrays.toString(joinPoint.getArgs()));
    }
}
