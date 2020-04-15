package edu.sjsu.cmpe275.cartpool.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@XmlRootElement
@Table(name = "pooler",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"screenname", "nickname"})})
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

    @Column(name = "screenname")
    private String screenname;

    @Column(name = "nickname")
    private String nickname;
}
