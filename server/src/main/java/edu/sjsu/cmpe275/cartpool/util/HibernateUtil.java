//package edu.sjsu.cmpe275.cartpool.util;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//import org.springframework.stereotype.Component;
//
//@Component
//public class HibernateUtil {
//
//    private static final SessionFactory sessionFactory;
//
//    static {
//        Configuration configuration = new Configuration().configure();
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
//                .applySettings(configuration.getProperties());
//        sessionFactory = configuration.buildSessionFactory(builder.build());
//    }
//
//    HibernateUtil(){
//    }
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//}