package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.CartItems;
import com.finalprojectcoffee.entities.Product;
import jakarta.persistence.*;

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
            Cart cart = getOrCreateCart(userId, entityManager);

            CartItems existingCartItem = findCartItemByProductID(cart.getId(), productId, entityManager);

            if (existingCartItem != null) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            } else {
                Product product = entityManager.find(Product.class, productId);
                if (product == null) {
                    return false;
                }
                double cost = product.getPrice() * quantity;
                CartItems cartItem = new CartItems(cart, productId, cost, quantity);
                entityManager.persist(cartItem);
            }
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            System.err.println("An Exception has occurred when adding item to cart: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

    private Cart getOrCreateCart(int userID, EntityManager entityManager) {
        Cart cart = findCartByUserId(userID, entityManager);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userID);
            entityManager.persist(cart);
        }
        return cart;
    }

    private CartItems findCartItemByProductID(int cartId, int productId, EntityManager entityManager) {
        Query q = entityManager.createQuery("SELECT ci FROM CartItems ci WHERE ci.cart.id=:cartId AND ci.product_id=:productId");
        q.setParameter("userId", cartId);
        q.setParameter("productId", productId);

        try {
            return (CartItems) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("There has been no Cart Item Found");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Cart findCartByUserId(int userId, EntityManager entityManager) {
        Query q = entityManager.createQuery("SELECT c FROM Cart c WHERE c.userId=:userId");
        q.setParameter("userId", userId);

        try {
            return (Cart) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("There has been no Cart Found");
            System.out.println(e.getMessage());
            return null;
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

            CartItems cartItem = entityManager.find(CartItems.class, cartId);
            if (cartItem != null) {
                cartItem.setQuantity(quantity);
                entityManager.merge(cartItem);
            }

            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            System.err.println("An Exception has occurred when updating quantity of item in cart: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<CartItems> getCartItems(int userId) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            Query q = entityManager.createQuery("SELECT ci FROM CartItems ci WHERE ci.cart.userId = :userId");
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
            Query q = entityManager.createQuery("SELECT SUM(ci.cost) FROM CartItems ci WHERE ci.cart.userId = :userId");
            q.setParameter("userId", userId);
            Double result = (Double) q.getSingleResult();
            return result != null ? result : 0.0;
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

            Query q = entityManager.createQuery("DELETE FROM CartItems ci WHERE ci.cart.userId = :userId");
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
            Query q = entityManager.createQuery("SELECT ci FROM CartItems ci WHERE ci.cart.userId = :userId AND ci.product_id = :productId");
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