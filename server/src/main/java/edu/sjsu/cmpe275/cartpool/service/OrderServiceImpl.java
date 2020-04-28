package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.exceptions.OrderNotFoundException;
import edu.sjsu.cmpe275.cartpool.exceptions.StoreNotFoundException;
import edu.sjsu.cmpe275.cartpool.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.cartpool.pojos.Orders;
import edu.sjsu.cmpe275.cartpool.pojos.Pool;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.pojos.Store;
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

    public Orders createOrder(Orders order, String deliveryPersonId, String ownerId, long storeId) {
        Pooler deliveryPerson = poolerRepository.findByEmail(deliveryPersonId); //.orElseThrow(() -> new UserNotFoundException());
        Pooler owner = poolerRepository.findByEmail(ownerId);//.orElseThrow(() -> new UserNotFoundException());
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException());
        order.setDeliveryBy(deliveryPerson);
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
		if(ownerOrders.size()<1) throw new OrderNotFoundException();
		return ownerOrders;
	}

	public List<Orders> getOrdersForPickUp(long poolerId, long storeId) {
		Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException());
		Pooler pooler = poolerRepository.findById(poolerId).orElseThrow(() -> new UserNotFoundException());
		Pool poolerPool = pooler.getPool();
		List<Orders> orders = orderRepository.findByPoolAndStoreAndAvailableAndForDelivery(poolerPool, store, true, true);
		if (orders.size() < 1) throw new OrderNotFoundException();
		return orders;
	}

	public List<Orders> getAllOrdersForPickup(long id) {
		Pooler owner = poolerRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
		List<Orders> orders = owner.getOrders();
		if(orders.size()<1) throw new OrderNotFoundException();
		List<Orders> ownerOrders = orderRepository.findByOrderOwner(owner);
		orders.addAll(orderRepository.findByOrderOwnerAndAvailable(owner, true));
		return orders;
	}
	
	public List<Orders> getDeliveryOrders(long id) {
		Pooler owner = poolerRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
		List<Orders> orders = owner.getOrders();
		if(orders.size()<1) throw new OrderNotFoundException();
		return orders;
	}

	public Boolean selectOrders(long poolerId, int count, String[] orders) {
		Pooler deliveryBy = poolerRepository.findById(poolerId).orElseThrow(() -> new UserNotFoundException());
		for(String order : orders) {
			long orderNum = Long.parseLong(order);
			Orders currentOrder = orderRepository.findById(orderNum).orElseThrow(() -> new OrderNotFoundException());
			currentOrder.setDeliveryBy(deliveryBy);
			currentOrder.setAvailable(false);
		}
		return deliveryBy.getOrders().size()>0;
	}

	public void pickUpOrderForDelivery(long deliveryPersonId, long orderId) {
		Orders order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
		if (order.getOrderOwner() != null) {
			String status = order.getOrderOwner().getId() == deliveryPersonId ? Constants.PICKED_UP_BY_SELF : Constants.PICKED_UP;
			order.setStatus(status);
			//To-do

			orderRepository.save(order);
		} else {
			throw new UserNotFoundException();
		}
	}

	public void markOrderDelivered(long orderId) {
		Orders order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
		order.setStatus(Constants.DELIVERED);
		//To-do

		orderRepository.save(order);
	}

}
