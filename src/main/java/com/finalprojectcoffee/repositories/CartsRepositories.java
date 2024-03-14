package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Carts;
import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Product;
import jakarta.persistence.*;

import java.util.List;

/**
 * @author cgarvey00
 */
public class CartsRepositories implements CartsRepositoriesInterface {
    private final EntityManagerFactory factory;

    public CartsRepositories(EntityManagerFactory factory) {
        this.factory = factory;
    }

    /**
     * This method will add a cart entity into the database through
     * persistence
     *
     * @param cart the cart supplied into the method
     * @return carts the object returned indicating a success following persisting into
     * the database
     * @throws PersistenceException where a cart did not get added
     */
    @Override
    public Carts addCart(Carts cart) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(cart);
            transaction.commit();
            return cart;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while adding a cart: " + e.getMessage());
            return null;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    /**
     * This method will add a cart through the customer id, product id and the quantity
     * this method is more commonly used as it provides greater usability and is effective
     * when testing
     *
     * @param customerId the customer id of the customer purchasing the product
     * @param productId  the product id representing the product added
     * @param quantity   the quantity supplied of which is purchased
     * @return Boolean indicating that the cart item is persisted to the database
     * @throws PersistenceException where a cart did not get added
     */
    @Override
    public Boolean addCartItem(int customerId, int productId, int quantity) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Product product = entityManager.find(Product.class, productId);

            if (product == null || product.getStock() < quantity) {
                System.err.println("Product is not available due to insufficient Product " + productId);
                return false;
            }

            TypedQuery<Carts> query = entityManager.createQuery("SELECT c FROM Carts c WHERE c.customer.id = :customerId AND c.product.id=:productId", Carts.class);

            query.setParameter("customerId", customerId);
            query.setParameter("productId", productId);

            List<Carts> cartItems = query.getResultList();

            if (!cartItems.isEmpty()) {
                System.err.println("Cart Item already exists for customer " + customerId + " and Product " + productId);
                return false;
            }

            Customer customer = entityManager.find(Customer.class, customerId);

            double total_cost = product.getPrice() * quantity;

            Carts cartItem = new Carts(product, customer, quantity, total_cost);

            entityManager.persist(cartItem);

            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("An PersistenceException occurred while persisting: " + e.getMessage());
            return false;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }

    }

    /**
     * This method will update a cart through the customer id, product id and the quantity
     * this method is used to update the product quantity using the product id and the customer id
     * representing
     *
     * @param customerId the customer id of the customer updating the product
     * @param productId  the product id representing the product quantity being updated
     * @param quantity   the quantity supplied of which will may or be not be purchased
     * @return Boolean indicating that the cart item has been merged to the database
     * @throws PersistenceException where a cart did not get merged
     */
    @Override
    public Boolean updateCartItem(int customerId, int productId, int quantity) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            TypedQuery<Carts> query = entityManager.createQuery("SELECT c FROM Carts c WHERE c.customer.id = :customerId AND c.product.id=:productId", Carts.class);

            Product product = entityManager.find(Product.class, productId);

            if (product == null || product.getStock() < quantity || quantity < 0) {
                System.err.println("Product is not available due to insufficient Product " + productId);
                return false;
            }


            query.setParameter("customerId", customerId);
            query.setParameter("productId", productId);

            List<Carts> cartItems = query.getResultList();

            if (cartItems.isEmpty()) {
                System.err.println("Cart Item is not available for customer " + customerId + " and Product " + productId);
                return false;
            }

            Carts cartItem = cartItems.get(0);
            cartItem.setQuantity(quantity);
            double total_cost = product.getPrice() * quantity;
            cartItem.setTotalCost(total_cost);

            entityManager.merge(cartItem);

            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("An PersistenceException occurred while persisting: " + e.getMessage());
            return false;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }

    }

    /**
     * This method will retrieve a cart through the cart id containing the product and the customer who has it stored
     *
     * @param cartId the cartId id of item of which the customer owns containing a product
     * @return Cart the entity containing the cart object
     * @throws PersistenceException where a cart did not get retrieved
     */
    @Override
    public Carts getCartById(int cartId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<Carts> query = entityManager.createQuery("SELECT c FROM Carts c WHERE c.id = :cartId", Carts.class);
            query.setParameter("cartId", cartId);
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("An Exception has occurred when retrieving carts: " + e.getMessage());
            return null;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    /**
     * This method will retrieve a Customers cart items of products they have selected
     *
     * @param customerId the customer who has stored pending purchase or otherwise
     * @return List<Cart> a list of cart objects containing products owned by the customer
     * @throws PersistenceException where the carts did not get retrieved
     */
    @Override
    public List<Carts> getCartsByCustomerId(int customerId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<Carts> query = entityManager.createQuery("SELECT c FROM Carts c WHERE c.customer.id = :customerId", Carts.class);
            query.setParameter("customerId", customerId);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("An Exception has occurred when retrieving carts: " + e.getMessage());
            return null;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    /**
     * This method will remove a Customers cart items of products they wish to delete
     *
     * @param customerId the customer who has stored pending purchase or otherwise
     * @return Boolean which indicates if the items have been deleted from the cart or not
     * @throws PersistenceException where the carts did not get deleted
     */
    @Override
    public Boolean removeItemsFromCart(int customerId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Customer customer = entityManager.find(Customer.class, customerId);
            int deleteCount = entityManager.createQuery("DELETE FROM Carts c WHERE c.customer.id=:customerId").setParameter("customerId", customerId)
                    .executeUpdate();
            transaction.commit();

            return deleteCount > 0;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("An PersistenceException occurred while deleting: " + e.getMessage());
            return false;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    /**
     * This method will remove a Customers cart item of product they wish to delete using the customers id and product id
     *
     * @param customerId the customer who has a stored item pending deletion
     * @param productId the product which has been stored pending deletion
     * @return Boolean which indicates if the item have been deleted from the cart or not
     * @throws PersistenceException where the cart item did not get deleted
     */
    @Override
    public Boolean deleteCartItem(int customerId, int productId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            TypedQuery<Carts> query = entityManager.createQuery("SELECT c FROM Carts c WHERE c.customer.id=:customerId AND c.product.id=:productId", Carts.class);

            query.setParameter("customerId", customerId);
            query.setParameter("productId", productId);

            List<Carts> cartsList = query.getResultList();

            if (!cartsList.isEmpty()) {
                Carts cart = cartsList.get(0);
                entityManager.remove(cart);
                transaction.commit();
                return true;
            } else {
                transaction.commit();
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("An PersistenceException occurred while deleting: " + e.getMessage());
            return false;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    /**
     * This method will remove a Customers cart item of product they wish to delete using the customers id and product id
     *
     * @param customerId the customer who has a stored item pending deletion
     * @return Boolean which indicates if the item have been deleted from the cart or not
     * @throws PersistenceException where the cart item did not get deleted
     */
    @Override
    public Boolean clearCart(int customerId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            TypedQuery<Carts> query = entityManager.createQuery("SELECT c FROM Carts c WHERE c.customer.id=:customerId", Carts.class);

            query.setParameter("customerId", customerId);

            List<Carts> cartsList = query.getResultList();

            // Checking if an invalid CustomerID or Empty Cart is supplied
            if (!cartsList.isEmpty()) {

                for (Carts cart : cartsList) {
                    entityManager.remove(cart);
                }
                transaction.commit();
                return true;
            } else {
                transaction.commit();
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("An PersistenceException occurred while clearing cart: " + e.getMessage());
            return false;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

}
