package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class DBConnection {

    public static SessionFactory getSessionFactory() {
        StandardServiceRegistry registry = null;
        SessionFactory sessionFactory = null;

        try {
            registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log the error)
            e.printStackTrace();
        } finally {
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }

        return sessionFactory;
    }
}