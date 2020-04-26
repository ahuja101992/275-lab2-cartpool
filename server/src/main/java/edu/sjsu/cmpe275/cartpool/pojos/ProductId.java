package edu.sjsu.cmpe275.cartpool.pojos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductId implements Serializable {

    @Column(nullable = false,name="storeId")
    private Long storeId;

    @Column(name="sku")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sku;

    public ProductId(Long storeId,Long sku){
        this.sku=sku;
        this.storeId=storeId;
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

    public long getSku() {
        return sku;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }
}
