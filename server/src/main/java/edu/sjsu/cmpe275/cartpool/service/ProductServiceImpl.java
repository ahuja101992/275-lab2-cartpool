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

import java.util.HashSet;
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
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public List<Product> deleteProduct(String sku,Long adminId) {
        System.out.println("Deleting product: ");
        List<Product> products = productRepository.findProductsByOrderId(sku);
        HashSet<Product> set1= new HashSet<>(products);
        List<Product> productsku = productRepository.findProductsBySku(sku);
        HashSet<Product> set2= new HashSet<>(productsku);
        set2.removeAll(set1);
        if(set2!=null && set2.size()>0) {
            productRepository.deleteAll(set2);
        }else{

        }
        return searchProductsByAdminId(adminId);
    }

    @Override
    @Transactional
    public List<Product> updateProduct(List<Product> products,Long adminId) {
        for(Product product : products){
            List<Product> stored = searchProductBySKU(product.getId().getSku());
            productRepository.save(product);
        }
        return searchProductsByAdminId(adminId);

    }

    @Override
    @Transactional
    public Set<Product> searchProductByStoreId(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException());
        List<Product> list = productRepository.findByStoreId(storeId);
        return new HashSet<Product>(list);
    }

    @Override
    @Transactional
    public Product ffindByStoreId_SKU(Long storeId,String sku) {
        Product product = productRepository.findById(new ProductId(sku)).orElseThrow(()->new UserNotFoundException());
        return product;
    }

    @Override
    @Transactional
    public List<Product> searchProductBySKU(String sku) {
         return productRepository.findProductsBySku(sku);
    }

    @Override
    @Transactional
    public List<Product> searchProductByName(String name, Long storeId) {
        return productRepository.findByNameAndStoreId(name, storeId);
    }

    @Override
    @Transactional
    public List<Product> searchProductsByAdminId(Long adminId){
        return productRepository.findByAdmin_Id(adminId);
    }


    @Override
    @Transactional
    public List<Product> getProductsGroupByName(){
        return productRepository.findProductsGroupByName();
    }

}
