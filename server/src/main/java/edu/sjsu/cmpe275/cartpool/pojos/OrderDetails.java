package edu.sjsu.cmpe275.cartpool.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "items")
public class OrderDetails {

    @Column
    private long qty;

    @Column
    private long price;

    @Column
    private String sku;

    @Column
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "order_id", referencedColumnName = "id")
//    @JsonIgnoreProperties({"orderOwner","store","pool","deliveryBy"})
    private Orders order;

    public OrderDetails() {

    }
    public OrderDetails(long qty, long price, String sku, String name) {
        this.qty = qty;
        this.price = price;
        this.sku = sku;
        this.name=name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Long getQty() {
        return qty;
    }

    public Long getPrice() {
        return price;
    }

    public String getSku() {
        return sku;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
