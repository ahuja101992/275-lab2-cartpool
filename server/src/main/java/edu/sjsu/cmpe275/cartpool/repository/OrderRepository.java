package edu.sjsu.cmpe275.cartpool.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.cartpool.pojos.Orders;
import edu.sjsu.cmpe275.cartpool.pojos.Pool;

@Repository
public interface OrderRepository<T> extends CrudRepository<Orders, Long> {
}