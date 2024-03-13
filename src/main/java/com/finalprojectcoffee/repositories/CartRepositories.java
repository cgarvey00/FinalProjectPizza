package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.*;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

public class CartRepositories implements CartRepositoriesInterface {

    private final EntityManagerFactory factory;

    public CartRepositories(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Cart addCart() {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Cart cart = new Cart();
            entityManager.persist(cart);
            transaction.commit();
            return cart;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while adding an empty cart: " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public CartItem addItem(int cartId, int productId, int quantity) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Product product = entityManager.find(Product.class, productId);
            Cart cart = entityManager.find(Cart.class, cartId);
            CartItem cartItem = new CartItem();

            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cartItem.setCost(quantity * product.getPrice());
            product.setStock(product.getStock() - quantity);

            entityManager.persist(cartItem);
            entityManager.merge(product);
            transaction.commit();
            return cartItem;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("An PersistenceException occurred while persisting: " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Cart createCart(List<CartItem> cartItems) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Cart cart = new Cart();
            double totalCost = 0.0;

            for(CartItem cartItem : cartItems){
                totalCost += cartItem.getCost();
            }

            cart.setCartItems(cartItems);
            cart.setTotalCost(totalCost);

            entityManager.persist(cart);
            transaction.commit();
            return cart;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while persisting: " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean removeItemsFromCart(List<Integer> cartItemIds) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            for(int cartItemId : cartItemIds){
                CartItem cartItem = entityManager.find(CartItem.class, cartItemId);
                Product product = entityManager.find(Product.class, cartItem.getProduct().getId());
                int stock = cartItem.getQuantity();
                product.setStock(product.getStock() + stock);
                entityManager.remove(cartItem);
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
            TypedQuery<CartItem> query = entityManager.createQuery("SELECT c.cartItems FROM Cart c WHERE c.id = :cartId", CartItem.class);
            query.setParameter("cartId", cartId);
            return query.getResultList();
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