package org.example.userservice.common.utils;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.example.userservice.config.YamlConfig;
import org.example.userservice.domain.entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Map;

@ApplicationScoped
public class HibernateProducer {

    private SessionFactory sessionFactory;

    @ApplicationScoped
    @Produces
    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    @PreDestroy
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    private SessionFactory buildSessionFactory() {
        try {
            YamlConfig yamlConfig = new YamlConfig("application.yml");
            Map<String, Object> hibernate = yamlConfig.getHibernateConfig();

            Map<String, Object> connection = (Map<String, Object>) hibernate.get("connection");
            Map<String, Object> hbm2ddl = (Map<String, Object>) hibernate.get("hbm2ddl");

            Configuration cfg = new Configuration();

            cfg.setProperty("hibernate.connection.driver_class", connection.get("driver_class").toString());
            cfg.setProperty("hibernate.connection.url", connection.get("url").toString());
            cfg.setProperty("hibernate.connection.username", connection.get("username").toString());
            cfg.setProperty("hibernate.connection.password", connection.get("password").toString());
            cfg.setProperty("hibernate.dialect", hibernate.get("dialect").toString());
            cfg.setProperty("hibernate.show_sql", hibernate.get("show_sql").toString());
            cfg.setProperty("hibernate.format_sql", hibernate.get("format_sql").toString());
            cfg.setProperty("hibernate.hbm2ddl.auto", hbm2ddl.get("auto").toString());

            cfg.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();

            return cfg.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            throw new RuntimeException("Error building SessionFactory", e);
        }
    }
}
