package edu.sjsu.cmpe275.cartpool.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "pool")
public class Pool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "pool_id")
    private String poolId;

    @Column(name = "name")
    private String name;

    @Column(name = "neighborhoodName")
    private String neighborhoodName;

    @Column(name = "description")
    private String description;

    @Column(name = "zip")
    private String zip;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnoreProperties({"pool"})
    private Pooler poolLeader;


    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH},
            mappedBy = "pool"
    )
    @JsonIgnoreProperties({"pool"})
    private List<Pooler> members;


    public Pool() {
    }

    public Pool(PoolBuilder builder) {
        this.poolId = builder.poolId;
        this.name = builder.name;
        this.neighborhoodName = builder.neighborhoodName;
        this.description = builder.description;
        this.zip = builder.zip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
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

    public List<Pooler> getMembers() {
        return members;
    }

    public void setMembers(List<Pooler> members) {
        this.members = members;
    }

    public void addPooler(Pooler pooler){
        if(members == null)
            members = new ArrayList<>();

        members.add(pooler);
    }

    public static class PoolBuilder {
        private String poolId;
        private String name;
        private String neighborhoodName;
        private String description;
        private String zip;

        public PoolBuilder poolId(String poolId) {
            this.poolId = poolId;
            return this;
        }

        public PoolBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PoolBuilder neighborhoodName(String neighborhoodName) {
            this.neighborhoodName = neighborhoodName;
            return this;
        }

        public PoolBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PoolBuilder zip(String zip) {
            this.zip = zip;
            return this;
        }

        public Pool build() {
            return new Pool(this);
        }
    }
}
