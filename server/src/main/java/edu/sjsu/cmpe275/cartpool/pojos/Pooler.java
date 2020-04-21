package edu.sjsu.cmpe275.cartpool.pojos;

import javax.persistence.*;

@Entity
@Table(name = "pooler")
public class Pooler extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
        private boolean sauceInside = false; // Default

        public Builder() {}

        public Pooler build() { return new Pooler(this); }
    }
}
