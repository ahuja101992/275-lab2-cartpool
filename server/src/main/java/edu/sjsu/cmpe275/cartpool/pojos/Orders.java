package edu.sjsu.cmpe275.cartpool.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @XmlTransient
    @JsonIgnoreProperties({"admin", "orders", "products", "address"})
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pool_id")
    @XmlTransient
    @JsonIgnoreProperties("pool")
    private Pool pool;

    @Column
    private long qty;

    @Column
    private long price;

    @Column
    private long finalPrice;

    @Column
    private boolean available;

    @Column
    private boolean forDelivery;

    @Column
    private String status;

    @OneToOne(fetch = FetchType.LAZY)
    private Pooler orderOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pooler_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"deliveryBy"})///// to be done 
    private Pooler deliveryBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderDetails> orderItems;

    public Orders() {
    }

    protected Orders(OrderBuilder orderBuilder) {
        this.available = orderBuilder.available;
        this.qty = orderBuilder.qty;
        this.price = orderBuilder.price;
        this.finalPrice = orderBuilder.finalPrice;
        this.forDelivery = orderBuilder.forDelivery;
        this.status = orderBuilder.status;
    }

    public long getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(long finalPrice) {
        this.finalPrice = finalPrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isForDelivery() {
        return forDelivery;
    }

    public void setForDelivery(boolean forDelivery) {
        this.forDelivery = forDelivery;
    }

    public Pooler getOrderOwner() {
        return orderOwner;
    }

    public void setOrderOwner(Pooler orderOwner) {
        this.orderOwner = orderOwner;
    }

    public Pooler getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(Pooler deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class OrderBuilder {
        private long store_id;
        private int qty;
        private long price;
        private long finalPrice;
        private boolean forDelivery;
        private boolean available;
        private String status;
        private Pooler orderOwner;

        public OrderBuilder store_id(long store_id) {
            this.store_id = store_id;
            return this;
        }

        public OrderBuilder qty(int qty) {
            this.qty = qty;
            return this;
        }

        public OrderBuilder price(long price) {
            this.price = price;
            return this;
        }

        public OrderBuilder finalPrice(long finalPrice) {
            this.finalPrice = finalPrice;
            return this;
        }

        public OrderBuilder forDelivery(boolean forDelivery) {
            this.forDelivery = forDelivery;
            return this;
        }


        public OrderBuilder available(boolean available) {
            this.available = available;
            return this;
        }


        public OrderBuilder status(String status) {
            this.status = status;
            return this;
        }

        public OrderBuilder orderOwner(Pooler orderOwner) {
            this.orderOwner = orderOwner;
            return this;
        }

        public Orders build() {
            return new Orders(this);
        }
    }
}
