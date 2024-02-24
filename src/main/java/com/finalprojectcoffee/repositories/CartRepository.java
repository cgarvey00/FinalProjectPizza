package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.CartItem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.util.Collections;
import java.util.List;

public class CartRepository implements CartRepositoryInterface {

    private EntityManagerFactory factory;

    public CartRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean addToCart(int userId, int productId, int quantity) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Cart cart = findByUserId(userId);
            if (cart == null) {
                cart = new Cart(userId);
                entityManager.persist(cart);
            }
            CartItem existingItem = findByUserIdAndProductId(userId, productId);
            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                entityManager.merge(existingItem);
            } else {
                CartItem newItem = new CartItem(cart, productId, 0, quantity);
                entityManager.persist(newItem);
            }
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while adding to cart: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void removeFromCart(int cartId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Cart cart = entityManager.find(Cart.class, cartId);
            if (cart != null) {
                entityManager.remove(cart);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while removing from cart: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updateQuantity(int cartId, int quantity) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            CartItem cartItem = entityManager.find(CartItem.class, cartId);
            if (cartItem != null) {
                cartItem.setQuantity(quantity);
                entityManager.merge(cartItem);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while updating cart item quantity: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<CartItem> getCartItems(int userId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            return entityManager.createQuery("SELECT ci FROM CartItem ci WHERE ci.cart.userId = :userId", CartItem.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("An Exception occurred while getting cart items: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public double getTotalCost(int userId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            return entityManager.createQuery("SELECT SUM(ci.cost) FROM CartItem ci WHERE ci.cart.userId = :userId", Double.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (Exception e) {
            System.err.println("An Exception occurred while getting total cost: " + e.getMessage());
            return 0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void clearCart(int userId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.createQuery("DELETE FROM CartItem ci WHERE ci.cart.userId = :userId")
                    .setParameter("userId", userId)
                    .executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while clearing cart: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Cart findByUserIdAndProductId(int userId, int productId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            return entityManager.createQuery("SELECT ci.cart FROM CartItem ci WHERE ci.cart.userId = :userId AND ci.productId = :productId", Cart.class)
                    .setParameter("userId", userId)
                    .setParameter("productId", productId)
                    .getSingleResult();
        } catch (Exception e) {
            System.err.println("An Exception occurred while finding cart by user ID and product ID: " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    private Cart findByUserId(int userId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            return entityManager.createQuery("SELECT c FROM Cart c WHERE c.userId = :userId", Cart.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            entityManager.close();
        }
    }
}
