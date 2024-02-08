package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.List;

public class CartRepositories implements CartRepositoriesInterface {

    private final EntityManagerFactory factory;

    public CartRepositories(EntityManagerFactory factory) {
        this.factory = factory;
    }


    @Override
    public boolean addToCart(int userId, int productId, int quantity) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Cart existingCartItem = findByUserIdAndProductId(userId, productId);
            if(existingCartItem !=null){
                return false;
            }else {
                Cart cartItem = new Cart();
                cartItem.setUserId(userId);
                cartItem.setProductId(productId);
                cartItem.setQuantity(quantity);
                entityManager.persist(cartItem);
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("An Exception has occurred when adding item to cart: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void removeFromCart(int cartId) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Cart cartItem = entityManager.find(Cart.class, cartId);
            if (cartItem != null) {
                entityManager.remove(cartItem);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("An Exception has occurred when removing item from cart: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updateQuantity(int cartId, int quantity) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Cart cartItem = entityManager.find(Cart.class, cartId);
            if (cartItem != null) {
                cartItem.setQuantity(quantity);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("An Exception has occurred when updating quantity of item in cart: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Cart> getCartItems(int userId) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            Query q = entityManager.createQuery("SELECT c FROM Cart c WHERE c.userId = :userId");
            q.setParameter("userId", userId);
            return q.getResultList();
        } catch (Exception e) {
            System.err.println("An Exception has occurred when retrieving cart items: " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public double getTotalCost(int userId) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            Query q = entityManager.createQuery("SELECT SUM(c.cost) FROM Cart c WHERE c.userId = :userId");
            q.setParameter("userId", userId);
            return (Double) q.getSingleResult();
        } catch (Exception e) {
            System.err.println("An Exception has occurred when calculating total cost of cart items: " + e.getMessage());
            return 0.0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void clearCart(int userId) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Query q = entityManager.createQuery("DELETE FROM Cart c WHERE c.userId = :userId");
            q.setParameter("userId", userId);
            q.executeUpdate();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("An Exception has occurred when clearing cart: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Cart findByUserIdAndProductId(int userId, int productId) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            Query q = entityManager.createQuery("SELECT c FROM Cart c WHERE c.userId = :userId AND c.productId = :productId");
            q.setParameter("userId", userId);
            q.setParameter("productId", productId);
            return (Cart) q.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            entityManager.close();
        }
    }
}
