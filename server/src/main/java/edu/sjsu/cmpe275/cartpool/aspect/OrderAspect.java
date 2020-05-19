package edu.sjsu.cmpe275.cartpool.aspect;

import edu.sjsu.cmpe275.cartpool.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.cartpool.pojos.OrderDetails;
import edu.sjsu.cmpe275.cartpool.pojos.Orders;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.repository.PoolerRepository;
import edu.sjsu.cmpe275.cartpool.service.EmailService;
import edu.sjsu.cmpe275.cartpool.service.OrderService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Aspect
public class OrderAspect {
    @Autowired
    public EmailService emailService;

    @Autowired
    public OrderService orderService;

    @Autowired
    PoolerRepository<Pooler> poolerRepository;

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

    @AfterReturning(pointcut = "execution(public * edu.sjsu.cmpe275.cartpool.controller.OrderController.checkout(..))", returning = "result")
    public void sendOrderDeliveredEmail(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();

        System.out.println(result);
        System.out.println(result.getClass());
        System.out.println("[OrderCheckoutAspect] sendDeliveryInstructionEmail =" + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(public * edu.sjsu.cmpe275.cartpool.service.OrderServiceImpl.markDeliveryNotReceived(..))", returning = "result")
    public void sendDeliveryNotReceivedEmail(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();

        System.out.println("sendDeliveryNotReceivedEmail");
        Orders order = (Orders) result;

//        String to = order.getDeliveryByPrev();
        long ownerId = order.getDeliveryByPrev().getId();
        Pooler toPooler = poolerRepository.findById(ownerId).orElseThrow(() -> new UserNotFoundException());
        String to = toPooler.getEmail();
        String subject = "[CartPool]: Delivery not received for OrderId: " + order.getId();

        StringBuilder str = new StringBuilder();
        List<OrderDetails> items = order.getOrderDetails();
        if (items.size() > 0) {
            for (OrderDetails item : items) {
                str.append("------>" + item.getId() + "   " + item.getQty() + "   " + item.getPrice() + "\n");
            }
        }

        String msg = "Delivery not received for OrderId: " + order.getId() + " with items - \n\n" + str;

        emailService.sendEmailForOrderConfirmation(to, subject, msg);

        System.out.println("[OrderCheckoutAspect] sendDeliveryInstructionEmail =" + Arrays.toString(joinPoint.getArgs()));
    }
}
