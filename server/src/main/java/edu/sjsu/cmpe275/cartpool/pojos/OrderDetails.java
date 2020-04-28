package edu.sjsu.cmpe275.cartpool.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "items")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private final Long qty;

    @Column
    private final Long price;

    @Column
    private final Long sku;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Orders order;

    public OrderDetails(long qty, long price, long sku) {
        this.qty = qty;
        this.price = price;
        this.sku = sku;
    }
}
