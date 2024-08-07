package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.ProductRepositoriesInterface;
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
    public Product getMostPopularProduct() {
        EntityManager entityManager = factory.createEntityManager();

        try {
            String queryStr = "SELECT p FROM Product p JOIN OrdersItem oi ON p.id=oi.product.id " +
                    "GROUP BY p.id ORDER BY SUM(oi.quantity) DESC";
            TypedQuery<Product> query = entityManager.createQuery(queryStr, Product.class);
            query.setMaxResults(1);
            List<Product> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } catch (Exception e) {
            System.err.println("An Exception has occurred when Searching for the most popular product " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Object[]> getTopSellingCategories() {
        EntityManager entityManager = factory.createEntityManager();

        try {
            String queryStr = "SELECT p.category, SUM(oi.quantity) AS totalQuantity"+
                    " FROM OrdersItem oi JOIN oi.product p GROUP BY p.category ORDER BY totalQuantity DESC";
            Query query=entityManager.createQuery(queryStr);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("An Exception has occurred when Searching for the top selling category" + e.getMessage());
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

            for (Product product : products) {
                TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class);
                query.setParameter("name", product.getName());

                List<Product> existingProducts = query.getResultList();
                if (existingProducts.isEmpty()) {
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
    public boolean deleteProduct(List<Product> products) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {

            if (products == null || products.isEmpty()) {
                return false;
            }

            transaction.begin();

            for (Product product : products) {
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

    public void resetAutoIncrement(String tableName) {
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
