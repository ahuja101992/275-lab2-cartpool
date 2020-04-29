package edu.sjsu.cmpe275.cartpool.aspect;

import edu.sjsu.cmpe275.cartpool.service.EmailService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class OrderAspect {
    @Autowired
    public EmailService emailService;

    @AfterReturning(pointcut = "execution(public * edu.sjsu.cmpe275.cartpool.controller.OrderController.checkout1(..))", returning = "result")
    public void sendCheckoutSuccessEmail(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();

        System.out.println(result);
        System.out.println(result.getClass());
        System.out.println("[OrderCheckoutAspect] sendCheckoutSuccessEmail =" + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(public * edu.sjsu.cmpe275.cartpool.controller.OrderController.checkout1(..))", returning = "result")
    public void sendDeliveryInstructionEmail(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();

        System.out.println(result);
        System.out.println(result.getClass());
        System.out.println("[OrderCheckoutAspect] sendDeliveryInstructionEmail =" + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(public * edu.sjsu.cmpe275.cartpool.controller.OrderController.checkout1(..))", returning = "result")
    public void sendOrderDeliveredEmail(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();

        System.out.println(result);
        System.out.println(result.getClass());
        System.out.println("[OrderCheckoutAspect] sendDeliveryInstructionEmail =" + Arrays.toString(joinPoint.getArgs()));
    }
}
