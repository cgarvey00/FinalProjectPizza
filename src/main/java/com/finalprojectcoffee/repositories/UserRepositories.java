package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.utils.JwtUtil;
import jakarta.persistence.*;

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
            User user = entityManager.find(User.class,userId);
            return user;
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
            Query query =entityManager.createQuery("SELECT u From User u Where u.username = :username");
            query.setParameter("username",username);
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<User> findAllUsersByIds() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM User u");
            List<User> users = query.getResultList();
            return users;
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public User findUserByUsernamePassword(String username, String password) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password");
            query.setParameter("username",username);
            query.setParameter("password",password);
            User user = (User)query.getSingleResult();
            return user;
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching " + e.getMessage());
            return null;
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
            System.err.println("An PersistenceException occurred while merging " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }
}