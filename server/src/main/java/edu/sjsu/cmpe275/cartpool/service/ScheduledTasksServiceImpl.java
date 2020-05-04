package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Orders;
import edu.sjsu.cmpe275.cartpool.repository.OrderRepository;
import edu.sjsu.cmpe275.cartpool.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasksServiceImpl implements ScheduledTasksService {

    @Autowired
    OrderRepository<Orders> orderRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy 'at' HH:mm:ss z");

    private static final long numOfMsInTwoDays =  2 * 24 * 60 * 60 * 1000;

    @Scheduled(fixedRate = 120000)
    public void cancelOrderOlderThanTwoDays() {
        Date currentDate = new Date(System.currentTimeMillis());

        Iterable<Orders> orders = orderRepository.findAll();
        for (Orders order: orders) {
            Date orderDate = order.getDate();

            long diffInMillies = Math.abs(currentDate.getTime() - orderDate.getTime());

            if (diffInMillies >= numOfMsInTwoDays && !Constants.CANCELED.equals(order.getStatus())) {
                System.out.println("Canceled order.id: " + order.getId());
                System.out.println("diffInMillies: " + diffInMillies);

                order.setStatus(Constants.CANCELED);
                orderRepository.save(order);
            }
        }

    }
}
