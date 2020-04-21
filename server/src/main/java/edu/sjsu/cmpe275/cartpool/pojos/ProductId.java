package edu.sjsu.cmpe275.cartpool.pojos;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductId implements Serializable {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long storeId;

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sku;

    public ProductId(long storeId,long sku){
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
}
