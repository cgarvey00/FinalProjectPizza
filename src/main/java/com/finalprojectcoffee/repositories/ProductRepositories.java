package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import jakarta.persistence.*;

import java.util.List;

public class ProductRepositories implements ProductRepositoriesInterface {
    private final EntityManagerFactory factory;

    public ProductRepositories(EntityManagerFactory factory) {
        this.factory = factory;
    }


    @Override
    public Product findProductByID(int productID) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            Product p = entityManager.find(Product.class, productID);
            return p;
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
            Query q = entityManager.createQuery("SELECT p FROM Product p");
            return q.getResultList();
        } catch (Exception e) {
            System.err.println("An Exception has occurred when Searching for a List of Product " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Product> findProductsByCategory(ProductCategory category) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            Query q = entityManager.createQuery("SELECT p FROM Product p WHERE p.category=:category");
            q.setParameter("category", category);
            return q.getResultList();
        } catch (Exception e) {
            System.err.println("An Exception has occurred when Searching for Products of a specific category " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Product> findProductsByKeyword(String keyword) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            Query q = entityManager.createQuery("SELECT p FROM Product p WHERE LOWER(p.name)LIKE LOWER(:keyword)");
            q.setParameter("keyword", "%" + keyword + "%");
            return q.getResultList();
        } catch (Exception e) {
            System.err.println("An Exception has occurred when Searching for Products of a specific keyword " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean addProduct(Product p) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(p);
            transaction.commit();
            return true;
        } catch (PersistenceException pe) {
            System.err.println(pe.getMessage());
            System.err.println("A Persistence Exception occurred while Adding the Product\n\t" + p);
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean updateProduct(int id, String name, ProductCategory category, String details, double price, int stock, String image) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Product p = entityManager.find(Product.class, id);

            if (p != null) {
                p.setCategory(category);
                p.setDetails(details);
                p.setName(name);
                p.setPrice(price);
                p.setImage(image);
                p.setStock(stock);
                entityManager.merge(p);
                transaction.commit();
                return true;
            } else {
                System.err.println("Product with ID  +" + id + " not available");
                return false;
            }
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            System.err.println("A Persistence Exception occurred while Updating the Product\n\t" + e);
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean deleteProduct(int pID) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Product p = entityManager.find(Product.class, pID);
            entityManager.remove(p);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            System.err.println("A Persistence Exception occurred while Deleting the Product\n\t" + e);
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }
}
