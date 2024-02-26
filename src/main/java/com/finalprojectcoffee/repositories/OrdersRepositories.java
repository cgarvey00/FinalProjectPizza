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
    public List<Order> getAllOrdersByCustomerId(int customerId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o WHERE o.customer.id = :customerId", Order.class);
            query.setParameter("customerId", customerId);
            List<Order> orders = query.getResultList();
            return orders;
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Order> getAllOrdersByEmployeeId(int employeeId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o WHERE o.employee.id = :employeeId", Order.class);
            query.setParameter("employeeId", employeeId);
            List<Order> orders = query.getResultList();
            return orders;
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean addOrder(int customerId, int cartId, int temporaryAddressId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Order order = new Order();
            Cart cart = entityManager.find(Cart.class, cartId);
            Customer customer = entityManager.find(Customer.class, customerId);

            if(customer != null && cart != null){
                order.setCart(cart);
                order.setCustomer(customer);
                order.setCreateTime(LocalDateTime.now());
                order.setOverdueTime(LocalDateTime.now().plusMinutes(30));
                order.setPaymentStatus(Status.Pending);
                order.setStatus(Status.Pending);
                order.setBalance(cart.getTotalCost());

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
    public Boolean payOrder(int orderId, double payment) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Order order = entityManager.find(Order.class, orderId);
            Query query = entityManager.createQuery("SELECT c FROM Cart c WHERE c.id = :orderId");
            query.setParameter("orderId", orderId);
            Cart cart = (Cart) query.getSingleResult();
            double totalCost = order.getCart().getTotalCost();

            order.setBalance(payment - totalCost);
            order.setBalance(0.0);
            order.setPaymentStatus(Status.Paid);
            order.setUpdateTime(LocalDateTime.now());

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
    public Boolean acceptOrders(List<Integer> orderIds) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            for(Integer orderId : orderIds){
                Order order = entityManager.find(Order.class, orderId);

                if(order.getStatus() == Status.Pending && order.getPaymentStatus() == Status.Paid){
                    order.setStatus(Status.Accepted);
                    order.setUpdateTime(LocalDateTime.now());
                    entityManager.merge(order);
                }
            }

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

    @Override
    public Boolean deliverOrders(List<Integer> orderIds, int employeeId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            List<Order> orders = new ArrayList<>();
            Employee employee = entityManager.find(Employee.class, employeeId);

            for(Integer orderId : orderIds){
                Order order = entityManager.find(Order.class, orderId);

                if(order != null && order.getPaymentStatus() == Status.Paid){
                    order.setEmployee(employee);
                    order.setStatus(Status.Delivering);
                    order.setUpdateTime(LocalDateTime.now());
                    orders.add(order);
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

    @Override
    public Boolean finishOrder(int orderId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Order order = entityManager.find(Order.class, orderId);
            order.setStatus(Status.Finished);

            entityManager.merge(order);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A Persistence occurred while merging: " + e.getMessage());
            return false;
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
