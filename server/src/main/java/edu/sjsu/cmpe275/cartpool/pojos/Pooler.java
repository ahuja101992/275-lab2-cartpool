package edu.sjsu.cmpe275.cartpool.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "pooler", uniqueConstraints = {@UniqueConstraint(columnNames = {"screenname", "nickname"})})
public class Pooler extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
    )
    private Pool pool;

    public Pooler() {
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

    public static class Builder extends User.Builder<Builder> {
        private final boolean sauceInside = false; // Default

        public Builder() {
        }

        public Pooler build() {
            return new Pooler(this);
        }
    }
}
