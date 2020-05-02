package edu.sjsu.cmpe275.cartpool.pojos;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductId implements Serializable {

    private Long storeId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String sku;

    public ProductId() {

    }

    public ProductId(Long storeId, String sku) {
        this.sku = sku;
        this.storeId = storeId;
    }

    public ProductId(Long storeId){
        this.storeId=storeId;
    }

    public ProductId(String sku){
        this.sku=sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductId productId = (ProductId) o;
        return storeId == productId.storeId &&
                sku == productId.sku;
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, sku);
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
