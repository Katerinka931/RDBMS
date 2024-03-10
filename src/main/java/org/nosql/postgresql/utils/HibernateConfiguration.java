package org.nosql.postgresql.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.nosql.postgresql.entity.*;

public class HibernateConfiguration {
    private static SessionFactory sessionFactory;

    public static SessionFactory getInstance() {
        if (sessionFactory == null) {
            sessionFactory = configureSessionFactory();
        }
        return sessionFactory;
    }

    private static SessionFactory configureSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Sport.class);
            configuration.addAnnotatedClass(Sportsman.class);
            configuration.addAnnotatedClass(Team.class);
            configuration.addAnnotatedClass(Contact.class);
            configuration.addAnnotatedClass(Achievement.class);

            StandardServiceRegistry standardServiceRegistryBuilder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            return configuration.buildSessionFactory(standardServiceRegistryBuilder);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
