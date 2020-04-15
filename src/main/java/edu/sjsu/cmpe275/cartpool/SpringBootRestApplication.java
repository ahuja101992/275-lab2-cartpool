package edu.sjsu.cmpe275.cartpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@SpringBootApplication(scanBasePackages = {"edu.sjsu"})
@EntityScan(basePackages = {"edu.sjsu.cmpe275.cartpool.pojos"})
@EnableJpaRepositories(basePackages = {"edu.sjsu.cmpe275.cartpool.repository"})
public class SpringBootRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(edu.sjsu.cmpe275.cartpool.SpringBootRestApplication.class, args);
    }
}
