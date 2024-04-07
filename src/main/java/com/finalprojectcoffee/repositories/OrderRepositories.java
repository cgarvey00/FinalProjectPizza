package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderRepositories implements OrderRepositoriesInterface {
    private EntityManagerFactory factory;
    public OrderRepositories(EntityManagerFactory factory){this.factory = factory;}

    public OrderRepositories(){}
    @Override
    public Order findOrderById(int orderId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            return entityManager.find(Order.class, orderId);
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
            return query.getResultList();
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
            TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o WHERE o.customer.id = :customerId ORDER BY o.createTime DESC", Order.class);
            query.setParameter("customerId", customerId);

            return query.getResultList();
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
            TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o WHERE o.employee.id = :employeeId ORDER BY o.createTime DESC", Order.class);
            query.setParameter("employeeId", employeeId);

            return query.getResultList();
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(int orderId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<OrderItem> query = entityManager.createQuery("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId", OrderItem.class);
            query.setParameter("orderId", orderId);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("An Exception occurred while searching: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Order addOrder(int customerId, int addressId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Order order = new Order();
            Customer customer = entityManager.find(Customer.class, customerId);
            Address address = entityManager.find(Address.class, addressId);

            if(customer != null && address != null){
                order.setCustomer(customer);
                order.setCreateTime(LocalDateTime.now());
                order.setPaymentStatus(Status.Pending);
                order.setStatus(Status.Pending);
                order.setAddress(address);

                entityManager.persist(order);
            }

            transaction.commit();
            return order;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while persisting: " + e.getMessage());
            transaction.rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean addOrderItem(List<OrderItem> orderItems) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            if(orderItems != null && !orderItems.isEmpty()){

                for(OrderItem orderItem : orderItems){
                    entityManager.persist(orderItem);
                }
            }

            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while adding order item: " + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean updateAddressInOrder(int orderId, int addressId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Order order = entityManager.find(Order.class, orderId);
            Address address = entityManager.find(Address.class, addressId);

            if (order != null && address != null) {
                order.setAddress(address);
                entityManager.merge(order);
            }

            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while merging: " + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean payOrder(int orderId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Order order = entityManager.find(Order.class, orderId);

            order.setPaymentStatus(Status.Paid);
            order.setUpdateTime(LocalDateTime.now());

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
    public void deliverOrder(int orderId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.status = :status", Employee.class);
            query.setParameter("status", Status.Available);

            List<Employee> employees = query.getResultList();
            if (!employees.isEmpty()) {
                //Get the employee which id is the minimum
                Employee employeeWithMinId = employees.stream()
                        .min(Comparator.comparing(Employee::getId))
                        .orElse(null);
                //Calculate current employee's order count
                employeeWithMinId.setCurrentOrderCount(employeeWithMinId.getCurrentOrderCount() + 1);
                if(employeeWithMinId.getCurrentOrderCount() == 5){
                    employeeWithMinId.setStatus(Status.Unavailable);
                }
                entityManager.merge(employeeWithMinId);
                //Get the order, and allocate employee for the order
                Order order = entityManager.find(Order.class, orderId);
                order.setEmployee(employeeWithMinId);
                order.setStatus(Status.Delivering);
                entityManager.merge(order);
            }

            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("A PersistenceException occurred while merging: " + e.getMessage());
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
    public Boolean cancelOrder(int orderId) {
        EntityManager  entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Order order = entityManager.find(Order.class, orderId);
            Query query = entityManager.createQuery("SELECT o FROM Order o WHERE o.id = :orderId");
            query.setParameter("orderId", orderId);

            if(order != null){
                order.setStatus(Status.Cancelled);
                order.setPaymentStatus(Status.Refunded);
                order.setUpdateTime(LocalDateTime.now());
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