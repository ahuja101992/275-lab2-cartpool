package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.exceptions.OrderNotFoundException;
import edu.sjsu.cmpe275.cartpool.exceptions.StoreNotFoundException;
import edu.sjsu.cmpe275.cartpool.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.cartpool.pojos.*;
import edu.sjsu.cmpe275.cartpool.repository.OrderRepository;
import edu.sjsu.cmpe275.cartpool.repository.PoolerRepository;
import edu.sjsu.cmpe275.cartpool.repository.StoreRepository;
import edu.sjsu.cmpe275.cartpool.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository<Orders> orderRepository;

    @Autowired
    PoolerRepository<Pooler> poolerRepository;

    @Autowired
    StoreRepository<Pooler> storeRepository;

    @Autowired
    EmailService emailService;

    public Orders createOrder(Orders order, String deliveryPersonId, String ownerId, long storeId) {
        Pooler deliveryPerson = null;
        if (deliveryPersonId != null && deliveryPersonId != "")
            deliveryPerson = poolerRepository.findByEmail(deliveryPersonId).orElseThrow(() -> new UserNotFoundException()); //.orElseThrow(() -> new UserNotFoundException());
        Pooler owner = poolerRepository.findByEmail(ownerId).orElseThrow(() -> new UserNotFoundException());//.orElseThrow(() -> new UserNotFoundException());
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException());
        Pool ownerPool = owner.getPool();
        order.setDeliveryBy(deliveryPerson);
        order.setPool(ownerPool);
        order.setOrderOwner(owner);
        order.setStore(store);
        return orderRepository.save(order);
    }

    public Orders getOrderDetails(long id) {
        Orders orderDetails = orderRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return orderDetails;
    }

    public List<Orders> getOrdersByOwnerId(long id) {
        Pooler owner = poolerRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
//		List<Orders> ownerOrders = orderRepository.findByOrderOwnerAndAvailable(owner, true);
        List<Orders> ownerOrders = orderRepository.findByOrderOwner(owner);
        if (ownerOrders.size() < 1) throw new OrderNotFoundException();
        return ownerOrders;
    }

    public List<Orders> getOrdersForPickUp(long poolerId, long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException());
        Pooler pooler = poolerRepository.findById(poolerId).orElseThrow(() -> new UserNotFoundException());
        Pool poolerPool = pooler.getPool();
        List<Orders> orders = orderRepository.findByPoolAndStoreAndAvailableAndForDeliveryOrderByDateAsc(poolerPool, store, true, true);
        if (orders.size() < 1) throw new OrderNotFoundException();
        return orders;
    }

    public List<Orders> getAllOrdersForPickup(long id) {
        Pooler owner = poolerRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        List<Orders> orders = owner.getOrders();
        orders.addAll(orderRepository.findByOrderOwnerAndAvailable(owner, true));
        if (orders.size() < 1) throw new OrderNotFoundException();

        return orders;
    }

    public List<Orders> getDeliveryOrders(long id) {
        Pooler owner = poolerRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        List<Orders> orders = owner.getOrders();
        //if (orders.size() < 1) throw new OrderNotFoundException();
        return orders;
    }

    public Boolean selectOrders(long poolerId, int count, List<Long> orders) {
        Pooler deliveryBy = poolerRepository.findById(poolerId).orElseThrow(() -> new UserNotFoundException());

        System.out.println("orders.length: " + orders.size()); //Added by Vini
        for (Long orderNum : orders) {
            System.out.println(orderNum); //Added by Vini
            Orders currentOrder = orderRepository.findById(orderNum).orElseThrow(() -> new OrderNotFoundException());
            currentOrder.setDeliveryBy(deliveryBy);
            currentOrder.setAvailable(false);

            orderRepository.save(currentOrder); //Added by Vini
        }

        //Added by Vini
        deliveryBy = poolerRepository.findById(poolerId).orElseThrow(() -> new UserNotFoundException());
        return deliveryBy.getOrders().size() > 0;
    }

    public void pickUpOrderForDelivery(long deliveryPersonId, long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
        if (order.getOrderOwner() != null) {
            String status = order.getOrderOwner().getId() == deliveryPersonId ? Constants.PICKED_UP_BY_SELF : Constants.PICKED_UP;
            order.setStatus(status);
            order.setAvailable(false);
            orderRepository.save(order);

            //To-do
            String to = order.getOrderOwner().getEmail();
            String subject = "[CartPool] - You order has been picked up";
            String messageBody = "You order has been picked up";
            emailService.sendEmailForOrderConfirmation(to, subject, messageBody);


            Pooler deliveryPerson = poolerRepository.findById(deliveryPersonId).orElse(null);
            if (deliveryPerson != null) {
                to = deliveryPerson.getEmail();

                Address address = order.getOrderOwner().getAddress();
                String addressInPlainText = address.getStreet() + " " + address.getCity() + " " + address.getState() + " " + address.getZip();

                StringBuilder str = new StringBuilder();
                List<OrderDetails> items = order.getOrderDetails();
                if (items.size() > 0) {
                    for (OrderDetails item : items) {
                        str.append("------> Id - " + item.getId() +  "  Name - " + item.getName() + "  Quantity - " + item.getQty() + "   Price - " + item.getPrice() + "\n");
                    }
                }
                subject = "[CartPool] - Order delivery instructions";
                messageBody = "Delivery has been assigned to you." +
                        "\n\nOrder id -" + order.getId() +
                        "\n\nAddress - " + addressInPlainText +
                        "\n\n with items - \n\n" + str;

                emailService.sendEmailForOrderConfirmation(to, subject, messageBody);
            }


        } else {
            throw new UserNotFoundException();
        }
    }

    public void markOrderDelivered(long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
        order.setStatus(Constants.DELIVERED);
        order.setDeliveryByPrev(order.getDeliveryBy());
        order.setDeliveryBy(null);
        //To-do

        orderRepository.save(order);

        String to = order.getOrderOwner().getEmail();
        String subject = "[CartPool] - You order has been delivered";
        String messageBody = "You order with id - " + orderId + " has been delivered";
        emailService.sendEmailForOrderConfirmation(to, subject, messageBody);
    }

    public Orders markDeliveryNotReceived(long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
        order.setStatus(Constants.DELIVERED_NOT_RECEIVED);

        return orderRepository.save(order);
    }

    public String generateOrderEmail(long id) {
        List<Orders> orders = getAllOrdersForPickup(id);
        StringBuilder str = new StringBuilder();
        for (Orders order : orders) {
            str.append(order.getId() + "   " + order.getQty() + "   " + order.getFinalPrice());
            str.append(System.getProperty("line.separator"));
            str.append("Details");
            List<OrderDetails> items = order.getOrderDetails();
            if (items.size() > 0) {
                for (OrderDetails item : items) {
                    str.append("------> Id - " + item.getId() +  "  Name - " + item.getName() + "  Quantity - " + item.getQty() + "   Price - " + item.getPrice() + "\n");
                }
            }
            str.append(System.getProperty("line.separator"));
        }
        return str.toString();
    }

    public void sendOrderConfirmationEmail(Orders order) {
        Pooler owner = order.getOrderOwner();
        String to = owner.getEmail();
        String subject = "";
        String msg = "";
        if (order.isForDelivery()) {
            subject = "[CartPool] - Order places Successfully with Order Id :" + order.getId();
            msg = "Hello " + owner.getNickname() + ",\n\nYour order has been successfully placed. It will be delivered by some fellow pooler shortly.\n\n Thanks for using CartPool.";
        } else {
            subject = "[CartPool] - Order details for all orders for pickup";
            msg = generateOrderEmail(owner.getId());
        }

        emailService.sendEmailForOrderConfirmation(to, subject, msg);
    }

	@Override
	public List<Orders> getActiveOrders(long storeId) {
		Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException());
		List<Orders> allOrders = orderRepository.findByStoreAndStatus(store, Constants.PLACED);
		allOrders.addAll(orderRepository.findByStoreAndStatus(store, Constants.DELIVERED_NOT_RECEIVED));
		allOrders.addAll(orderRepository.findByStoreAndStatus(store, Constants.PICKED_UP));
		allOrders.addAll(orderRepository.findByStoreAndStatus(store, Constants.PICKED_UP_BY_SELF));
		return allOrders;
	}
}
