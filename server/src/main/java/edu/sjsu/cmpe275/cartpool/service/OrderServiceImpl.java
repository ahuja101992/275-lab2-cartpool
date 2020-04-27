package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.exceptions.OrderNotFoundException;
import edu.sjsu.cmpe275.cartpool.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.cartpool.pojos.Orders;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.pojos.Store;
import edu.sjsu.cmpe275.cartpool.repository.OrderRepository;
import edu.sjsu.cmpe275.cartpool.repository.PoolerRepository;
import edu.sjsu.cmpe275.cartpool.repository.StoreRepository;
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
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new UserNotFoundException());
        order.setDeliveryBy(deliveryPerson);
        order.setOrderOwner(owner);
        order.setStore(store);
        return orderRepository.save(order);
    }

    public Orders getOrderDetails(long id) {
        Orders orderDetails = orderRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return orderDetails;
    }

    public List<Orders> getOrdersByUserId(long id) {
        Pooler pooler = poolerRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return pooler.getOrders();
    }

	@Override
	public List<Orders> getOrdersByOwnerId(long id) {
		Pooler owner = poolerRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
		List<Orders> ownerOrders = orderRepository.findByOrderOwnerAndAvailable(owner, true);
//		List<Orders> ownerOrders = orderRepository.findByAvailableAndQty(true, 12);
		if(ownerOrders.size()<1) throw new OrderNotFoundException();
		return ownerOrders;
	}

}
