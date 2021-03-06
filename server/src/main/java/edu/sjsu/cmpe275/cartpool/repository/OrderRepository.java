package edu.sjsu.cmpe275.cartpool.repository;

import edu.sjsu.cmpe275.cartpool.pojos.Orders;
import edu.sjsu.cmpe275.cartpool.pojos.Pool;
import edu.sjsu.cmpe275.cartpool.pojos.Pooler;
import edu.sjsu.cmpe275.cartpool.pojos.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository<T> extends CrudRepository<Orders, Long> {
    Optional<Orders> findById(Long id);

    List<Orders> findByOrderOwnerAndAvailable(Pooler owner, Boolean available);

    List<Orders> findByOrderOwner(Pooler owner); // for all orders

    List<Orders> findByPoolAndStoreAndAvailableAndForDeliveryOrderByDateAsc(Pool pool, Store store, Boolean available, Boolean delivery);

    List<Orders> findByPoolAndStatus(Pool pool, String status);

    List<Orders> findByStoreAndStatus(Store store, String status);
}
