package edu.sjsu.cmpe275.cartpool.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "product")
public class Product {

    @EmbeddedId
    private ProductId id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "imageURL")
    private String imageURL;

    @Column(name = "brandName")
    private String brandName;

    @Column(name = "qty")
    private Float qty;

    @Column(name = "unit")
    private String unit;

    @Column(name = "price")
    private Float price;

    //    @MapsId("AdminId")
    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Admin admin;

    @MapsId("StoreId")
    @ManyToOne
    @JoinColumn(name = "storeId", referencedColumnName = "id")
    private Store store;

    public Product() {
    }

    public Product(ProductId id, String name, String description, String imageURL, String unit, Float price, Float qty, Admin admin) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.unit = unit;
        this.price = price;
        this.qty = qty;
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.unit = unit;
        this.price = price;
        this.qty = qty;
        this.admin = admin;
    }


    public Product(ProductId id, String name, String description, String imageURL, String brandName, String unit, Float price, Float qty, Admin admin) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.brandName = brandName;
        this.unit = unit;
        this.price = price;
        this.qty = qty;
        this.admin = admin;
    }

    public Product(ProductId id) {
        this.id = id;
    }

    public ProductId getId() {
        return id;
    }

    public void setId(ProductId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id.toString() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", brandName='" + brandName + '\'' +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", store=" + store +
                '}';
    }

}
