package edu.sjsu.cmpe275.cartpool.pojos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="pool")
public class Pool {

    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "neighborhoodName")
    private String neighborhoodName;

    @Column(name = "description")
    private String description;

    @Column(name = "zip")
    private String zip;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name="id")
    private Pooler poolLeader;

    public Pool() {
    }

    public Pool(String name, String neighborhoodName, String description, String zip) {
        this.name = name;
        this.neighborhoodName = neighborhoodName;
        this.description = description;
        this.zip = zip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeighborhoodName() {
        return neighborhoodName;
    }

    public void setNeighborhoodName(String neighborhoodName) {
        this.neighborhoodName = neighborhoodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Pooler getPoolLeader() {
        return poolLeader;
    }

    public void setPoolLeader(Pooler poolLeader) {
        this.poolLeader = poolLeader;
    }

    @Override
    public String toString() {
        return "Pool{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", neighborhoodName='" + neighborhoodName + '\'' +
                ", description='" + description + '\'' +
                ", zip='" + zip + '\'' +
                ", poolLeader=" + poolLeader +
                '}';
    }
}
