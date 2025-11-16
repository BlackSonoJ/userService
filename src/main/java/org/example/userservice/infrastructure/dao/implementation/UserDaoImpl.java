package org.example.userservice.infrastructure.dao.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.userservice.common.exceptions.DatabaseAccessException;
import org.example.userservice.domain.entities.User;
import org.example.userservice.infrastructure.dao.UserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@ApplicationScoped
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Inject
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public User findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public User save(User user) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DatabaseAccessException("User was not saved " + e.getMessage());
        }
    }

    @Override
    public User update(Long id, User user) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            User oldUser = session.get(User.class, id);
            if (oldUser == null) {
                throw new DatabaseAccessException("User with id " + id + " not found");
            }
            if (user.getName() != null) {
                oldUser.setName(user.getName());
            }
            if (user.getEmail() != null) {
                oldUser.setEmail(user.getEmail());
            }
            if (user.getAge() != 0) { // assuming age cannot be 0 in your domain
                oldUser.setAge(user.getAge());
            }
            session.merge(oldUser);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DatabaseAccessException("User was not updated " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) session.remove(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DatabaseAccessException("User was not deleted " + e.getMessage());
        }
    }
}
