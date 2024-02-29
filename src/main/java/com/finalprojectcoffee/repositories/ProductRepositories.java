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
            Product product = entityManager.find(Product.class, productId);
            return product;
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
    public boolean addProducts(List<Product> products) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            for(Product product : products){
                TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class);
                query.setParameter("name", product.getName());

                List<Product> existingProducts = query.getResultList();
                if(existingProducts.isEmpty()){
                    entityManager.persist(product);
                } else {
                    System.err.println("Product exists: " + product.getName());
                }
            }

            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while Adding the Product: " + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean updateProduct(int id, String name, ProductCategory category, String details, double price, int stock, String image) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Product product = entityManager.find(Product.class, id);

            product.setCategory(category);
            product.setDetails(details);
            product.setName(name);
            product.setPrice(price);
            product.setImage(image);
            product.setStock(stock);
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
    public boolean deleteProduct(List<Product> products) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            for(Product product : products){
                TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.id = :id", Product.class);
                query.setParameter("id", product.getId());

                try {
                    Product deleteProduct = query.getSingleResult();
                    entityManager.remove(deleteProduct);
                } catch (NoResultException e) {
                    System.err.println(e.getMessage());
                    System.err.println("Product doesn't exist");
                }
            }

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
