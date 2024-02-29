package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.User;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

public class UserRepositories implements UserRepositoryInterfaces {
    private EntityManagerFactory factory;

    public UserRepositories(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public UserRepositories() {
    }

    @Override
    public User findUserById(int userId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            return entityManager.find(User.class,userId);
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public User findUserByUsername(String username) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            Query query =entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username");
            query.setParameter("username",username);
            return (User) query.getSingleResult();
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);

            try {
                List<User> users = query.getResultList();
                return users;
            } catch (NoResultException e) {
                System.err.println(e.getMessage());
                System.err.println("Users are not found");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean addUser(User user) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.persist(user);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while persisting " + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean updateUser(int userId, String password, String phoneNumber, String email, String image) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            User user = entityManager.find(User.class,userId);
            user.setPassword(password);
            user.setPhoneNumber(phoneNumber);
            user.setEmail(email);
            user.setImage(image);

            entityManager.merge(user);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while merging " + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean deleteUser(int userId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            User user = entityManager.find(User.class,userId);

            entityManager.remove(user);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while removing" + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean updateAddress(int userId, String street, String town, String county, String eirCode) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            User user = entityManager.find(User.class, userId);

            Address address = new Address();
            address.setUser(user);
            address.setStreet(street);
            address.setTown(town);
            address.setCounty(county);
            address.setEirCode(eirCode);

            entityManager.merge(address);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while merging: " + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }
}