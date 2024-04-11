package org.example.dao.hibernate;

import org.example.dao.IVehicleRepository;
import org.example.model.User;
import org.example.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class HibernateVehicleRepo implements IVehicleRepository {
    SessionFactory sessionFactory;
    private static HibernateVehicleRepo instance;

    private HibernateVehicleRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public static HibernateVehicleRepo getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new HibernateVehicleRepo(sessionFactory);
        }
        return instance;
    }


    @Override
    public boolean rentVehicle(String plate, String login) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            User user = session.get(User.class, login);
            Vehicle vehicle = session.get(Vehicle.class, plate);

            if (user != null && vehicle != null && user.rentedVehicle == null) {
                vehicle.user = user;
                vehicle.rent = true;
                user.rentedVehicle = vehicle;

                session.saveOrUpdate(user);
                session.saveOrUpdate(vehicle);

                transaction.commit();
                return true;
            } else {
                if (transaction != null) {
                    transaction.rollback();
                }
                return false;
            }
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    @Override
    public void addVehicle(Vehicle vehicle) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(vehicle);
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
    public void removeVehicle(String plate) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Vehicle vehicle = session.get(Vehicle.class, plate);
            if (vehicle != null && vehicle.user==null) {
                session.remove(vehicle);
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
    public Vehicle getVehicle(String plate) {
        Session session = sessionFactory.openSession();
        try {
            Vehicle vehicle = session.get(Vehicle.class, plate);
            return vehicle;
        } finally {
            session.close();
        }
    }

    //Must implement old interface. Plate is no longer needed when User has Vehicle.
    @Override
    public boolean returnVehicle(String plate,String login) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            User user = session.get(User.class, login);
            //Vehicle vehicle = user.rentedVehicle;

            if (user != null && user.rentedVehicle != null) {
                user.rentedVehicle.user = null;
                user.rentedVehicle.rent = false;

                session.saveOrUpdate(user.rentedVehicle);
                user.rentedVehicle = null;
                session.saveOrUpdate(user);


                transaction.commit();
                return true;
            } else {
                if (transaction != null) {
                    transaction.rollback();
                }
                return false;
            }
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

    }

    @Override
    public Collection<Vehicle> getVehicles() {
        Collection<Vehicle> vehicles;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            vehicles = session.createQuery("FROM Vehicle", Vehicle.class).getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return vehicles;
    }
}
