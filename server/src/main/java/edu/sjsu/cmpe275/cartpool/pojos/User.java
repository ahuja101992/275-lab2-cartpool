package edu.sjsu.cmpe275.cartpool.pojos;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class User {
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

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String provider_id;

    @Column(name = "contribution", columnDefinition = "integer default 0")
    private int contribution;

    @Embedded
    private Address address;

    public User() {
    }

    public User(Builder<?> builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.screenname = builder.screenname;
        this.nickname = builder.nickname;
        this.address = builder.address;
        this.accessToken = builder.accessToken;
        this.provider = builder.provider;
        this.provider_id = builder.provider_id;
//        this.contribution = builder.contribution;
    }

    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
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
        private String email;
        private String password;
        private String screenname;
        private String nickname;
        private Address address;
        private String provider;
        private String accessToken;
        private String provider_id;
//        private int contribution;

        public Builder() {
        }
//        public T contribution(int contribution) {
//            this.contribution = contribution;
//            return (T) this;
//        }

        public T provider_id(String provider_id) {
            this.provider_id = provider_id;
            return (T) this;
        }

        public T accessToken(String accessToken) {
            this.accessToken = accessToken;
            return (T) this;
        }

        public T provider(String provider) {
            this.provider = provider;
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
