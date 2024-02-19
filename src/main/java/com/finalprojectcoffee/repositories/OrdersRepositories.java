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
            return entityManager.find(Order.class,orderId);
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
    public Order findOrderByCustomer(Customer customer) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT o FROM Order o WHERE o.customer = :customer");
            query.setParameter("customer", customer);
            return (Order) query.getSingleResult();
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Order findOrderByEmployee(Employee employee) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT o FROM Order O WHERE o.employee = :employee");
            query.setParameter("employee", employee);
            return (Order) query.getSingleResult();
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean makeOrder(int cartId, int customerId, int temporaryAddressId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Cart cart = entityManager.find(Cart.class, cartId);
            Customer customer = entityManager.find(Customer.class, customerId);
            if(customer != null && cart != null){
                Order order = new Order();
                order.setCart(cart);
                order.setCustomer(customer);
                order.setStatus(Status.Pending);
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
    public Boolean acceptOrders(List<Integer> orderIds, Employee employee) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        List<Order> orders = new ArrayList<>();

        try {
            transaction.begin();

            for(Integer orderId : orderIds){
                Order order = entityManager.find(Order.class, orderId);

                if(order != null && order.getStatus() == Status.Pending){
                    order.setEmployee(employee);
                    order.setStatus(Status.Accepted);
                    order.setUpdateTime(LocalDateTime.now());
                    orders.add(order);
                }
            }

            entityManager.merge(orders);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while merging: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }
}
