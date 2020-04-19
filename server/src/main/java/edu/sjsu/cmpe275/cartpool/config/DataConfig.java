package edu.sjsu.cmpe275.cartpool.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
public class DataConfig {
    private final String PROPERTY_DRIVER = "spring.datasource.driverClassName";
    private final String PROPERTY_URL = "spring.datasource.url";
    private final String PROPERTY_USERNAME = "spring.datasource.username";
    private final String PROPERTY_PASSWORD = "spring.datasource.password";
    private final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
    private final String PROPERTY_DIALECT = "spring.jpa.database-platform";
    private final String PROPERTY_DDL_AUTO = "hibernate.hbm2ddl.auto";
    private final String PROPERTY_GENERATE_DDL = "hibernate.generate-ddl";
    private final String PROPERTY_NAMING = "spring.jpa.hibernate.naming.physical-strategy";

    @Autowired
    Environment environment;

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lfb = new LocalContainerEntityManagerFactoryBean();
        lfb.setDataSource(dataSource());
        lfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        lfb.setPackagesToScan("edu.sjsu.cmpe275.cartpool.pojos");
        lfb.setJpaProperties(hibernateProps());
        return lfb;
    }

    @Bean
    DataSource dataSource() {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("environment.getProperty(PROPERTY_URL): " + environment.getProperty(PROPERTY_URL));
        System.out.println("environment.getProperty(PROPERTY_USERNAME): " + environment.getProperty(PROPERTY_USERNAME));
        System.out.println("environment.getProperty(PROPERTY_PASSWORD): " + environment.getProperty(PROPERTY_PASSWORD));
        System.out.println("environment.getProperty(PROPERTY_DRIVER): " + environment.getProperty(PROPERTY_DRIVER));
        System.out.println("environment.getProperty(PROPERTY_DDL_AUTO): " + environment.getProperty(PROPERTY_DDL_AUTO));

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(environment.getProperty(PROPERTY_URL));
        ds.setUsername(environment.getProperty(PROPERTY_USERNAME));
        ds.setPassword(environment.getProperty(PROPERTY_PASSWORD));
        ds.setDriverClassName(environment.getProperty(PROPERTY_DRIVER));
        return ds;
    }

    Properties hibernateProps() {
        Properties properties = new Properties();
        properties.setProperty(PROPERTY_DIALECT, environment.getProperty(PROPERTY_DIALECT));
        properties.setProperty(PROPERTY_DDL_AUTO, environment.getProperty(PROPERTY_DDL_AUTO));
        properties.setProperty(PROPERTY_SHOW_SQL, environment.getProperty(PROPERTY_SHOW_SQL));
        properties.setProperty(PROPERTY_NAMING, environment.getProperty(PROPERTY_NAMING));
        properties.setProperty(PROPERTY_GENERATE_DDL, environment.getProperty(PROPERTY_GENERATE_DDL));

        return properties;
    }

    @Bean
    JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}


