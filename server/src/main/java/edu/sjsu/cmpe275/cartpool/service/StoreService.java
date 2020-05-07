package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.pojos.Product;
import edu.sjsu.cmpe275.cartpool.pojos.Store;

import java.util.List;


public interface StoreService {
    Store createStore(Store store, Long adminId);

    List<Store> deleteStore(Long storeId, Long adminId);

    List<Store> updateStore(Store store, Long adminId);

    Store findStore(Long storeId, Long adminId);
    
    List<Store> getAllStores();
}
