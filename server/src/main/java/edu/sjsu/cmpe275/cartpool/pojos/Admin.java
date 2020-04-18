package edu.sjsu.cmpe275.cartpool.pojos;

import javax.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "screenname")
    private String screenname;

    @Column(name = "nickname")
    private String nickname;

    @Embedded
    private Address address;

    public Admin() {
    }

    public Admin(AdminBuilder builder) {
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
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

    public static class AdminBuilder {
        private String firstname;
        private String lastname;
        private String email;
        private String screenname;
        private String nickname;
        private Address address;

        public AdminBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public AdminBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public AdminBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AdminBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public AdminBuilder screenname(String screenname) {
            this.nickname = screenname;
            return this;
        }

        public AdminBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }
    }
}
