package edu.sjsu.cmpe275.cartpool.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "pooler", uniqueConstraints = {@UniqueConstraint(columnNames = {"screenname", "nickname"})})
public class Pooler extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "pool_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"poolLeader"})
    private Pool pool;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy="deliveryBy")
    @JsonIgnoreProperties({"orderOwner", "deliveryBy"})
    private List<Orders> orders;

    public Pooler() {
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    protected Pooler(Builder builder) {
        super(builder);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

    public static class Builder extends User.Builder<Builder> {
        private final boolean sauceInside = false; // Default

        public Builder() {
        }

        public Pooler build() {
            return new Pooler(this);
        }
    }
}
