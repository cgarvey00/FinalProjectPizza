package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
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
        try {
            entityManager.getTransaction().begin();
            Cart cart = getOrCreateCart(userId, entityManager);


            CartItem existingCartItem = findCartItemByProductId(cart.getId(), productId, entityManager);
            if (existingCartItem != null) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            } else {

                CartItem newCartItem = new CartItem();
                newCartItem.setCart(cart);
                newCartItem.setProductId(productId);
                newCartItem.setQuantity(quantity);
                entityManager.persist(newCartItem);
            }

            entityManager.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            System.err.println("Error adding product to cart: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void removeFromCart(int cartItemId) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();


            CartItem cartItem = entityManager.find(CartItem.class, cartItemId);
            if (cartItem != null) {
                entityManager.remove(cartItem);
            }

            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            System.err.println("Error removing item from cart: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updateQuantity(int cartItemId, int quantity) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();


            CartItem cartItem = entityManager.find(CartItem.class, cartItemId);
            if (cartItem != null) {
                cartItem.setQuantity(quantity);
            }

            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            System.err.println("Error updating quantity of item in cart: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<CartItem> getCartItems(int userId) {
        EntityManager entityManager = factory.createEntityManager();
        try {

            Query query = entityManager.createQuery("SELECT ci FROM CartItem ci WHERE ci.cart.userId = :userId", CartItem.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public double getTotalCost(int userId) {
        EntityManager entityManager = factory.createEntityManager();
        try {

            Query query = entityManager.createQuery("SELECT SUM(ci.cost) FROM CartItem ci WHERE ci.cart.userId = :userId");
            query.setParameter("userId", userId);
            Double totalCost = (Double) query.getSingleResult();
            return totalCost != null ? totalCost : 0.0;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void clearCart(int userId) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();


            Query query = entityManager.createQuery("DELETE FROM CartItem ci WHERE ci.cart.userId = :userId");
            query.setParameter("userId", userId);
            query.executeUpdate();

            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            System.err.println("Error clearing cart: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Cart findByUserIdAndProductId(int userId, int productId) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            // Find a cart item by user ID and product ID
            Query query = entityManager.createQuery("SELECT ci.cart FROM CartItem ci WHERE ci.cart.userId = :userId AND ci.productId = :productId");
            query.setParameter("userId", userId);
            query.setParameter("productId", productId);
            List<Cart> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            entityManager.close();
        }
    }

    private Cart getOrCreateCart(int userId, EntityManager entityManager) {
        Cart cart = findCartByUserId(userId, entityManager);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            entityManager.persist(cart);
        }
        return cart;
    }

    private CartItem findCartItemByProductId(int cartId, int productId, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.productId = :productId", CartItem.class);
        query.setParameter("cartId", cartId);
        query.setParameter("productId", productId);
        List<CartItem> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    private Cart findCartByUserId(int userId, EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT c FROM Cart c WHERE c.userId = :userId", Cart.class);
        query.setParameter("userId", userId);
        List<Cart> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
