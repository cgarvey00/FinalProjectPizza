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
            return entityManager.find(User.class, userId);
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
            Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username");
            query.setParameter("username", username);

            List<User> resultList = query.getResultList();
            if (resultList.isEmpty()) {
                return null;
            } else {
                return resultList.get(0);
            }

//            return (User) query.getSingleResult();
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public User findUserByEmail(String email) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email");
            query.setParameter("email", email);
            List<User> resultList = query.getResultList();
            if (resultList.isEmpty()) {
                return null;
            } else {
                return resultList.get(0);
            }

//            return (User) query.getSingleResult();
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
    public Boolean updateUser(int userId, String phoneNumber, String email, String image) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            User user = entityManager.find(User.class, userId);
//            user.setPassword(password);
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
    public Boolean updatePassword(int userId, String password) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            User user = entityManager.find(User.class, userId);
            user.setPassword(password);
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

            User user = entityManager.find(User.class, userId);

            if (user != null) {
                entityManager.remove(user);
                transaction.commit();
                return true;
            } else {
                return false;
            }

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
    public Boolean updateAddress(int addressId, int userId, String street, String town, String county, String eirCode) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            User user = entityManager.find(User.class, userId);
            Address address = entityManager.find(Address.class, addressId);
//            Address address = new Address();
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

    @Override
    public Boolean deleteAddress(int addressId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Address address = entityManager.find(Address.class, addressId);

            entityManager.remove(address);
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

    public Address getAddressesByAddressId(int addressId) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.find(Address.class, addressId);
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }

    }

    public void resetAutoIncrement(String tableName) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT=4").executeUpdate();

            transaction.commit();
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            System.err.println("An Exception occurred when AutoIncrementing the Product ID\n\t" + e);
            transaction.rollback();

        } finally {
            entityManager.close();
        }
    }

    public void resetAutoIncrementAddress(String tableName) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT=2").executeUpdate();

            transaction.commit();
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            System.err.println("An Exception occurred when AutoIncrementing the Product ID\n\t" + e);
            transaction.rollback();

        } finally {
            entityManager.close();
        }
    }
}