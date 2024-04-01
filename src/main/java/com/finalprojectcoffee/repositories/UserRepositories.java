package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.Order;
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
            return query.getResultList();
        } catch (NoResultException e) {
            System.err.println("A NoResultException occurred while searching " + e.getMessage());
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
    public Boolean updateUser(int userId, String phoneNumber, String email) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            User user = entityManager.find(User.class,userId);
            user.setPhoneNumber(phoneNumber);
            user.setEmail(email);

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
    public Boolean addAddress(int userId, Address address) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            User user = entityManager.find(User.class, userId);
            address.setUser(user);

            entityManager.persist(address);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while persisting: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean updateAddress(Address address) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

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

    @Override
    public Boolean setDefaultAddress(Address address) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            if(address.getIsDefault() == 0){
                address.setIsDefault(1);
            } else {
                address.setIsDefault(0);
            }

            entityManager.merge(address);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while merging: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Address> getAddressesByUserId(int userId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<Address> query = entityManager.createQuery("SELECT a FROM Address a WHERE a.user.id = :userId", Address.class);
            query.setParameter("userId", userId);

            return query.getResultList();
        } catch (NoResultException e) {
            System.err.println("A NoResultException occurred while searching: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Address getAddressById(int addressId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            return entityManager.find(Address.class, addressId);
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching: " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean deleteAddress(int userId, int addressId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Address address = entityManager.find(Address.class, addressId);
            TypedQuery<Long> countQuery = entityManager.createQuery("SELECT COUNT(a) FROM Address a WHERE a.user.id = :userId", Long.class);
            countQuery.setParameter("userId", userId);
            long count = countQuery.getSingleResult();

            if(address.getIsDefault() == 1 && count > 1){
                TypedQuery<Address> query = entityManager.createQuery("SELECT a FROM Address a WHERE a.user.id = :userId AND a.id != :addressId ORDER BY a.id ASC", Address.class);
                query.setParameter("userId", userId);
                query.setParameter("addressId", addressId);
                List<Address> addresses = query.getResultList();
                if(!addresses.isEmpty()){
                    Address newDefaultAddress = addresses.get(0);
                    newDefaultAddress.setIsDefault(1);
                    entityManager.merge(newDefaultAddress);
                }
            }

            entityManager.remove(address);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while removing address: " + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Address getDefaultAddress(int userId) {
        EntityManager entityManager = factory.createEntityManager();

        Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.user.id = :userId and a.isDefault = 1");
        query.setParameter("userId", userId);
        return (Address) query.getSingleResult();
    }
}