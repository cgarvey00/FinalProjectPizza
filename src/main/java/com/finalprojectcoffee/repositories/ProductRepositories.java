package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

/**
 * @author cgarvey00
 */
public class ProductRepositories implements ProductRepositoriesInterface {
    private EntityManagerFactory factory;

    public ProductRepositories(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Product findProductByID(int productId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            return entityManager.find(Product.class, productId);
        } catch (Exception e) {
            System.err.println("There has been an Exception when Searching for a Product " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p", Product.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("An Exception has occurred when Searching for a List of Product " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Product> findProductsByCategory(ProductCategory category) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.category=:category", Product.class);
            query.setParameter("category", category);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("An Exception has occurred when Searching for Products of a specific category " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Product> findProductsByKeyword(String keyword) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE LOWER(p.name)LIKE LOWER(:keyword) OR LOWER(p.details)LIKE LOWER(:keyword) ", Product.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("An Exception has occurred when Searching for Products of a specific keyword " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean addProduct(Product product) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Query query = entityManager.createQuery("SELECT p FROM Product p WHERE p.name = :name");
            query.setParameter("name", product.getName());

            try {
                query.getSingleResult();
            } catch (NoResultException e) {
                entityManager.persist(product);
            }

            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while Adding product: " + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.merge(product);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A Persistence Exception occurred while Updating the Product: " + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean deleteProduct(int productId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            try {
                Product product = entityManager.find(Product.class, productId);
                entityManager.remove(product);
            } catch (NoResultException e) {
                System.err.println("Product doesn't exist: " + e.getMessage());
                return false;
            }

            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while Deleting the Product:" + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }
}
