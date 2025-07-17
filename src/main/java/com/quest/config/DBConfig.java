package com.quest.config;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBConfig {

    private DBConfig() {}

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure() // по умолчанию ищет hibernate.cfg.xml в resources
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + e);
        }
    }

    public static SessionFactory buildTestSessionFactory() {
        return new Configuration()
                .configure("hibernate-test.cfg.xml") // для тестов
                .buildSessionFactory();
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}