package org.example.userservice.infrastructure.dao;

import org.example.userservice.domain.entities.User;
import org.example.userservice.infrastructure.dao.implementation.UserDaoImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
class UserDaoImplTest {

    private static PostgreSQLContainer<?> postgres;

    private SessionFactory sessionFactory;
    private UserDaoImpl userDao;

    @BeforeAll
    static void beforeAll() {
        postgres = new PostgreSQLContainer<>("postgres:15-alpine")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setup() {
        Configuration cfg = new Configuration();

        cfg.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        cfg.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        cfg.setProperty("hibernate.connection.username", postgres.getUsername());
        cfg.setProperty("hibernate.connection.password", postgres.getPassword());
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        cfg.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        cfg.setProperty("hibernate.show_sql", "false");
        cfg.setProperty("hibernate.format_sql", "false");

        cfg.addAnnotatedClass(User.class);

        sessionFactory = cfg.buildSessionFactory();
        userDao = new UserDaoImpl(sessionFactory);
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }

    @Test
    void testSaveUser() {
        User user = new User( "Test", (short) 23, "example@mail.com");
        User result = userDao.save(user);

        assertNotNull(result.getId());
        assertEquals("Test", result.getName());
    }

    @Test
    void testFindAll() {
        User user1 = new User( "Test", (short) 23, "example1@mail.com");
        User user2= new User( "Test2", (short) 24, "example2@mail.com");

        userDao.save(user1);
        userDao.save(user2);

        List<User> users = userDao.findAll();
        assertEquals(2, users.size());
    }

    @Test
    void testFindById() {
        User user = new User( "Test", (short) 23, "example@mail.com");
        User saved = userDao.save(user);

        User found = userDao.findById(saved.getId());
        assertEquals("Test", found.getName());
        assertEquals(saved.getId(), found.getId());
    }

    @Test
    void testUpdateUser() {
        User user = new User( "Test", (short) 23, "example@mail.com");
        User saved = userDao.save(user);

        user.setName("NewName");
        user.setEmail("new@mail.com");
        user.setAge((short) 33);

        userDao.update(saved.getId(), user);

        User fromDb = userDao.findById(saved.getId());
        assertEquals(user.getName(), fromDb.getName());
        assertEquals(user.getEmail(), fromDb.getEmail());
        assertEquals(user.getAge(), fromDb.getAge());
    }

    @Test
    void testDeleteUser() {
        User user = new User( "Test", (short) 23, "example@mail.com");
        User saved = userDao.save(user);

        userDao.delete(saved.getId());

        assertNull(userDao.findById(saved.getId()));
    }
}
