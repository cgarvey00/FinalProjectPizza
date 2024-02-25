package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartRepositories implements CartRepositoriesInterface {

    private final EntityManagerFactory factory;

    public CartRepositories(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Boolean addItem(int cartId, int productId, int quantity) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Cart cart = entityManager.find(Cart.class, cartId);
            Product product = entityManager.find(Product.class, productId);
            CartItem cartItem = new CartItem();

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCost(quantity * product.getPrice());
            product.setStock(product.getStock() - quantity);

            entityManager.persist(cartItem);
            entityManager.merge(product);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("An PersistenceException occurred while persisting: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean addCart(int orderId, int cartId, List<Integer> cartItemIds) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Order order = entityManager.find(Order.class, orderId);
            List<CartItem> cartItems = new ArrayList<>();
            double totalCost = 0.0;
            Cart cart = new Cart();

            for(int cartItemId : cartItemIds){
                CartItem cartItem = entityManager.find(CartItem.class, cartItemId);
                cartItems.add(cartItem);
                totalCost += cartItem.getCost();
            }

            cart.setCartItems(cartItems);
            cart.setOrder(order);
            cart.setTotalCost(totalCost);

            entityManager.persist(cart);
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
    public Boolean removeItemsFromCart( List<Integer> cartItemIds) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            for(int cartItemId : cartItemIds){
                CartItem cartItem = entityManager.find(CartItem.class, cartItemId);
                Product product = entityManager.find(Product.class, cartItem.getProduct().getId());
                int stock = cartItem.getQuantity();
                entityManager.remove(cartItem);
                product.setStock(product.getStock() + stock);
            }

            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while removing: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<CartItem> getAllCartItemsByCartId(int cartId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<CartItem> query = entityManager.createQuery("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId", CartItem.class);
            query.setParameter("cartId", cartId);
            List<CartItem> cartItems = query.getResultList();
            return cartItems;
        } catch (Exception e) {
            System.err.println("An Exception has occurred when retrieving cart items: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public Boolean clearCart(int cartId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();


        try {
            transaction.begin();

            Cart cart = entityManager.find(Cart.class, cartId);
            List<CartItem> cartItems = cart.getCartItems();

            for (CartItem cartItem : cartItems) {
                int quantity = cartItem.getQuantity();
                Product product = cartItem.getProduct();
                product.setStock(product.getStock() + quantity);
                entityManager.merge(product);
            }

            entityManager.remove(cart);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while removing: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }
}