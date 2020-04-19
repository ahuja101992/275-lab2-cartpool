package edu.sjsu.cmpe275.cartpool.pojos;

import javax.persistence.*;

@Entity
@Table(name = "pooler")
public class Pooler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "screenname")
    private String screenname;

    @Column(name = "nickname")
    private String nickname;

    @Embedded
    private Address address;

    public Pooler() {
    }

    public Pooler(PoolerBuilder builder) {
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.password = builder.password;
        this.screenname = builder.screenname;
        this.nickname = builder.nickname;
        this.address = builder.address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getScreenname() {
        return screenname;
    }

    public void setScreenname(String screenname) {
        this.screenname = screenname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static class PoolerBuilder {
        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private String screenname;
        private String nickname;
        private Address address;

        public PoolerBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public PoolerBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public PoolerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PoolerBuilder password(String password) {
            this.password = password;
            return this;
        }

        public PoolerBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public PoolerBuilder screenname(String screenname) {
            this.screenname = screenname;
            return this;
        }

        public PoolerBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public Pooler build() {
            return new Pooler(this);
        }
    }
}
