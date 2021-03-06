package edu.sjsu.cmpe275.cartpool.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "pool")
public class Pool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "pool_id", unique = true)
    private String poolId;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "neighborhoodName")
    private String neighborhoodName;

    @Column(name = "description")
    private String description;

    @Column(name = "zip")
    @Pattern(regexp = "^[0-9]{5}")
    private String zip;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"pool"})
    private Pooler poolLeader;


    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "pool"
    )
    @JsonIgnoreProperties({"pool"})
    private Set<Pooler> members;


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

    public Set<Pooler> getMembers() {
        return members;
    }

    public void setMembers(Set<Pooler> members) {
        this.members = members;
    }

    public void addPooler(Pooler pooler) {
        if (members == null)
            members = new HashSet<>();

        members.add(pooler);
    }

    @Override
    public String toString() {
        return "Pool{" +
                "id=" + id +
                ", poolId='" + poolId + '\'' +
                ", name='" + name + '\'' +
                ", neighborhoodName='" + neighborhoodName + '\'' +
                ", description='" + description + '\'' +
                ", zip='" + zip + '\'' +
                ", poolLeader=" + poolLeader +
                ", members=" + members +
                '}';
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
