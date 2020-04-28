package edu.sjsu.cmpe275.cartpool.pojos;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "store",
        "pool",
        "qty",
        "items",
        "price",
        "finalPrice",
        "available",
        "forDelivery",
        "status",
        "orderOwner",
        "deliveryBy"
})
public class Cart {

    @JsonProperty("store")
    private Integer store;
    @JsonProperty("pool")
    private Object pool;
    @JsonProperty("qty")
    private Integer qty;
    @JsonProperty("items")
    private List<Item> items = null;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("finalPrice")
    private Integer finalPrice;
    @JsonProperty("available")
    private Boolean available;
    @JsonProperty("forDelivery")
    private Boolean forDelivery;
    @JsonProperty("status")
    private String status;
    @JsonProperty("orderOwner")
    private String orderOwner;
    @JsonProperty("deliveryBy")
    private String deliveryBy;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("store")
    public Integer getStore() {
        return store;
    }

    @JsonProperty("store")
    public void setStore(Integer store) {
        this.store = store;
    }

    @JsonProperty("pool")
    public Object getPool() {
        return pool;
    }

    @JsonProperty("pool")
    public void setPool(Object pool) {
        this.pool = pool;
    }

    @JsonProperty("qty")
    public Integer getQty() {
        return qty;
    }

    @JsonProperty("qty")
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @JsonProperty("price")
    public Integer getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Integer price) {
        this.price = price;
    }

    @JsonProperty("finalPrice")
    public Integer getFinalPrice() {
        return finalPrice;
    }

    @JsonProperty("finalPrice")
    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    @JsonProperty("available")
    public Boolean getAvailable() {
        return available;
    }

    @JsonProperty("available")
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @JsonProperty("forDelivery")
    public Boolean getForDelivery() {
        return forDelivery;
    }

    @JsonProperty("forDelivery")
    public void setForDelivery(Boolean forDelivery) {
        this.forDelivery = forDelivery;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("orderOwner")
    public String getOrderOwner() {
        return orderOwner;
    }

    @JsonProperty("orderOwner")
    public void setOrderOwner(String orderOwner) {
        this.orderOwner = orderOwner;
    }

    @JsonProperty("deliveryBy")
    public String getDeliveryBy() {
        return deliveryBy;
    }

    @JsonProperty("deliveryBy")
    public void setDeliveryBy(String deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}


