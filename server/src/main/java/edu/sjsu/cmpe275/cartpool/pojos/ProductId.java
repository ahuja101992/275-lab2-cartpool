package edu.sjsu.cmpe275.cartpool.pojos;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductId implements Serializable {

    private Long storeId;
    private Long sku;

    public ProductId(Long storeId, Long sku) {
        this.sku = sku;
        this.storeId = storeId;
    }

    public ProductId(Long sku){
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

    public long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }
}
