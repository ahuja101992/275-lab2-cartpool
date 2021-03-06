package edu.sjsu.cmpe275.cartpool.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "admin", uniqueConstraints = {@UniqueConstraint(columnNames = {"screenname", "nickname", "email"})})
public class Admin extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admin")
    @JsonIgnoreProperties({"orderOwner", "admin", "products"})
    @XmlTransient
    private List<Store> stores;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admin")
//    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"products", "admin", "store"})
    @XmlTransient
    private List<Product> products;

    public Admin() {
    }

    protected Admin(Builder builder) {
        super(builder);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public static class Builder extends User.Builder<Admin.Builder> {
        public Builder() {
        }

        public Admin build() {
            return new Admin(this);
        }
    }
}
