package edu.sjsu.cmpe275.cartpool.pojos;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class User {
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

    @Column(name = "is_verified")
    private boolean is_verified;

    @Embedded
    private Address address;

    public User() {
    }

    public User(Builder<?> builder) {
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.password = builder.password;
        this.screenname = builder.screenname;
        this.nickname = builder.nickname;
        this.address = builder.address;
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

    public boolean getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public static class Builder<T extends Builder<T>> {
        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private String screenname;
        private String nickname;
        private Address address;

        public Builder() {
        }

        public T firstname(String firstname) {
            this.firstname = firstname;
            return (T) this;
        }

        public T lastname(String lastname) {
            this.lastname = lastname;
            return (T) this;
        }

        public T email(String email) {
            this.email = email;
            return (T) this;
        }

        public T password(String password) {
            this.password = password;
            return (T) this;
        }

        public T nickname(String nickname) {
            this.nickname = nickname;
            return (T) this;
        }

        public T screenname(String screenname) {
            this.screenname = screenname;
            return (T) this;
        }

        public T address(Address address) {
            this.address = address;
            return (T) this;
        }

        public User build() {
            return new User(this);
        }
    }
}
