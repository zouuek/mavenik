package org.example.dao.hibernate;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.dao.IUserRepository;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class HibernateUserRepo implements IUserRepository {
    private static HibernateUserRepo instance;
    SessionFactory sessionFactory;
    @Override
    public User getUser(String login) {
        Session session = sessionFactory.openSession();
        User user = null;
        //Transaction transaction = null;
        user = session.get(User.class, login);
        session.close();
        //TODO: Finish this method
        return user;
    }

    @Override
    public void addUser(User user) {
        user.password = DigestUtils.sha256Hex(user.password);
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(user);

            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUser(String login) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, login);
            if (user != null && user.rentedVehicle==null) {
                session.remove(user);
            } else {
                return;
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Collection<User> getUsers() {
        Collection<User> users;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return users;
    }

    private HibernateUserRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public static HibernateUserRepo getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new HibernateUserRepo(sessionFactory);
        }
        return instance;
    }
}
