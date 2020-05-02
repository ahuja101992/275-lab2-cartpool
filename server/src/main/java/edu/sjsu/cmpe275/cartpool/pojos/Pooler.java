package edu.sjsu.cmpe275.cartpool.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "pooler", uniqueConstraints = {@UniqueConstraint(columnNames = {"screenname", "nickname", "email"})})
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryBy")
    @JsonIgnoreProperties({"orderOwner", "deliveryBy"})
    private List<Orders> orders;

    @Column(name = "isVerifiedForPoolMembership")
    private boolean isVerifiedForPoolMembership = false;
    
    @Column(name = "image")
    private String img ;

	public Pooler() {
    }

    protected Pooler(Builder builder) {
        super(builder);
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isVerifiedForPoolMembership() {
        return isVerifiedForPoolMembership;
    }

    public void setVerifiedForPoolMembership(boolean verifiedForPoolMembership) {
        isVerifiedForPoolMembership = verifiedForPoolMembership;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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
