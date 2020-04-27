package edu.sjsu.cmpe275.cartpool.service;

import edu.sjsu.cmpe275.cartpool.exceptions.StoreNotFoundException;
import edu.sjsu.cmpe275.cartpool.exceptions.UserNotFoundException;
import edu.sjsu.cmpe275.cartpool.pojos.Admin;
import edu.sjsu.cmpe275.cartpool.pojos.Product;
import edu.sjsu.cmpe275.cartpool.pojos.ProductId;
import edu.sjsu.cmpe275.cartpool.pojos.Store;
import edu.sjsu.cmpe275.cartpool.repository.AdminRepository;
import edu.sjsu.cmpe275.cartpool.repository.ProductRepository;
import edu.sjsu.cmpe275.cartpool.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    AdminRepository<Admin> adminRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoreRepository<Store> storeRepository;

    @Override
    @Transactional
    public Product createProduct(Product product, Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new UserNotFoundException());
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Set<Product> deleteProduct(Long storeId, Long sku, Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new UserNotFoundException());
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException());
        System.out.println("Deleting product: ");
        productRepository.deleteById(new ProductId(storeId, sku));
        return store.getProducts();
    }

    @Override
    @Transactional
    public Set<Product> updateProduct(Product product, Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new UserNotFoundException());
        productRepository.save(product);
        return product.getStore().getProducts();

    }

    @Override
    @Transactional
    public Set<Product> searchProductByStoreId(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException());
        return (Set<Product>) productRepository.findByStoreId(storeId);
    }

    @Override
    @Transactional
    public Product ffindByStoreId_SKU(Long storeId, Long sku) {
        Product product = productRepository.findById(new ProductId(storeId,sku)).orElseThrow(()->new UserNotFoundException());
        return product;
    }

    @Transactional
    public List<Product> searchProductBySKU(Long sku) {
        ProductId productId = new ProductId(sku);
        Example<Product> productExample = Example.of(new Product(productId));
        return productRepository.findAll(productExample);
    }

    @Transactional
    public List<Product> searchProductByName(String name) {
        return productRepository.findByName(name);
    }

}
