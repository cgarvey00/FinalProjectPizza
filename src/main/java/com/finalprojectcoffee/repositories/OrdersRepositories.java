package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrdersRepositories  implements OrdersRepositoriesInterface{
    private EntityManagerFactory factory;
    public OrdersRepositories(EntityManagerFactory factory){this.factory = factory;}

    public OrdersRepositories(){};
    @Override
    public Order findOrderById(int orderId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            Order order = entityManager.find(Order.class, orderId);
            return order;
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching: " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Order> getAllOrders() {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o", Order.class);
            List<Order> orders = query.getResultList();
            return orders;
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Order> getAllOrdersByUserId(int userId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o WHERE o.user.id = :userId", Order.class);
            query.setParameter("userId", userId);
            List<Order> orders = query.getResultList();
            return  orders;
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean addOrder(int userId, int cartId, int temporaryAddressId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Order order = new Order();
            Cart cart = entityManager.find(Cart.class, cartId);
            User user = entityManager.find(User.class, userId);

            if(user != null && cart != null){
                order.setCart(cart);
                order.setUser(user);
                order.setCreateTime(LocalDateTime.now());

                TemporaryAddress temporaryAddress = entityManager.find(TemporaryAddress.class, temporaryAddressId);
                if(temporaryAddress != null){
                    order.setTemporaryAddress(temporaryAddress);
                }

                entityManager.persist(order);
            }

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
    public Boolean payOrder(int orderId, int customerId, double payment) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Order order = entityManager.find(Order.class, orderId);
            Customer customer = entityManager.find(Customer.class, customerId);
            Query query = entityManager.createQuery("SELECT c FROM Cart c WHERE c.id = :orderId");
            query.setParameter("orderId", orderId);
            Cart cart = (Cart) query.getSingleResult();
            double totalCost = order.getCart().getTotalCost();

            if(customer != null && payment >= totalCost){
                order.setBalance(payment - totalCost);
                cart.setTotalCost(0.0);
                order.setPaymentStatus(Status.Paid);
                order.setUpdateTime(LocalDateTime.now());
            }

            entityManager.merge(cart);
            entityManager.merge(order);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("A PersistenceException occurred while merging: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Order> acceptOrders(List<Integer> orderIds) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            List<Order> orders = new ArrayList<>();

            for(Integer orderId : orderIds){
                Order order = entityManager.find(Order.class, orderId);

                if(order != null && order.getStatus() == Status.Pending && order.getPaymentStatus() == Status.Paid){
                    order.setStatus(Status.Accepted);
                    order.setUpdateTime(LocalDateTime.now());
                    orders.add(order);
                }
            }

            entityManager.merge(orders);
            transaction.commit();
            return orders;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while merging: " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Order> deliverOrders(List<Integer> orderIds, int userId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            List<Order> orders = new ArrayList<>();
            User user = entityManager.find(User.class, userId);

            for(Integer orderId : orderIds){
                Order order = entityManager.find(Order.class, orderId);

                if(order != null && order.getPaymentStatus() == Status.Paid){
                    order.setUser(user);
                    order.setStatus(Status.Delivering);
                    order.setUpdateTime(LocalDateTime.now());
                    orders.add(order);
                }
                entityManager.merge(order);
            }

            transaction.commit();
            return orders;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("A PersistenceException occurred while merging: " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean cancelOrders(List<Integer> orderIds) {
        EntityManager  entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            for(Integer orderId: orderIds){
                Order order = entityManager.find(Order.class, orderId);
                Query query = entityManager.createQuery("SELECT c FROM Cart c WHERE c.id = :orderId");
                query.setParameter("orderId", orderId);
                Cart cart = (Cart) query.getSingleResult();

                if(order != null){
                    order.setBalance(cart.getTotalCost());
                    order.setStatus(Status.Cancelled);
                    order.setPaymentStatus(Status.Refunded);
                    order.setUpdateTime(LocalDateTime.now());
                }

                entityManager.merge(order);
            }

            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("A PersistenceException occurred while merging: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }
}
