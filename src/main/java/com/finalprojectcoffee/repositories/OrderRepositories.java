package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yutang and cgarvey00
 */
public class OrderRepositories implements OrderRepositoriesInterface {
    private EntityManagerFactory factory;

    public OrderRepositories(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public OrderRepositories() {
    }

    @Override
    public Order findOrderById(int orderId) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            Order order = entityManager.find(Order.class, orderId);
            return order;
        } catch (Exception e) {
            System.err.println("There has been an Exception when Searching for a Product " + e.getMessage());
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
    public List<OrdersItem> getOrderItemsByOrder(Order order) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            TypedQuery<OrdersItem> query = entityManager.createQuery("SELECT oi FROM OrdersItem oi WHERE oi.orders.id=:orderId", OrdersItem.class);
            query.setParameter("orderId", order.getId());
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
    public Order addOrder(int customerId, int addressId, List<Carts> cartItems) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            if (customerId <= 0 || addressId <= 0 || cartItems.isEmpty() || cartItems == null ) {
                return null;
            }   transaction.begin();

            Customer customer = entityManager.find(Customer.class, customerId);
            Address address = entityManager.find(Address.class, addressId);

            if (!cartItems.isEmpty() || cartItems != null || customer != null || address != null) {
                TypedQuery<Carts> cartQuery = entityManager.createQuery("SELECT c FROM Carts c WHERE c.customer.id=:customerId", Carts.class);
                cartQuery.setParameter("customerId", customerId);
                Order order = new Order();

                order.setCustomer(customer);
                order.setCreateTime(LocalDateTime.now());
                order.setPaymentStatus(Status.Pending);
                order.setStatus(Status.Pending);
                order.setBalance(0.0);
                order.setAddress(address);
                entityManager.persist(order);
                for (Carts cartItem : cartItems) {
                    OrdersItem oItems = new OrdersItem();
                    oItems.setOrder(order);
                    oItems.setProduct(cartItem.getProduct());
                    oItems.setQuantity(cartItem.getQuantity());
                    oItems.setCost(cartItem.getTotalCost());

                    entityManager.persist(oItems);
                }
                transaction.commit();
                return order;
            } else {
                return null;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while persisting: " + e.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean payOrder(int orderId, double payment) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            if (orderId <= 0 || payment <= 0) {
                return false;
            }    transaction.begin();



            Order order = entityManager.find(Order.class, orderId);
            if (order != null) {
                Query query = entityManager.createQuery("SELECT o FROM Order o WHERE o.id = :orderId");
                query.setParameter("orderId", orderId);
                Order orderSingle = (Order) query.getSingleResult();

                orderSingle.setBalance(payment);
                orderSingle.setPaymentStatus(Status.Paid);
                orderSingle.setStatus(Status.Pending);
                orderSingle.setUpdateTime(LocalDateTime.now());

                entityManager.merge(orderSingle);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("A PersistenceException occurred while merging: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean deleteOrderDetails(int orderId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            if (orderId <= 0) {
                return false;
            } transaction.begin();



            Order order = entityManager.find(Order.class, orderId);

            if (order != null) {
                Query deleteOrderDetailsQuery = entityManager.createQuery("DELETE FROM OrdersItem oi WHERE oi.orders.id=:orderId");
                deleteOrderDetailsQuery.setParameter("orderId", orderId);
                deleteOrderDetailsQuery.executeUpdate();

                entityManager.remove(order);

                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while deleting order: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }


    @Override
    public Boolean acceptOrders(int orderId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            if (orderId <= 0) {
                return false;
            }transaction.begin();

            Order order = entityManager.find(Order.class, orderId);

            if (order != null) {

                if (order.getStatus() == Status.Pending && order.getPaymentStatus() == Status.Paid) {
                    order.setStatus(Status.Accepted);
                    order.setUpdateTime(LocalDateTime.now());
                    entityManager.merge(order);
                }
                transaction.commit();
                return true;
            } else {
                return false;
            }


        } catch (PersistenceException e) {
            transaction.rollback();
            System.err.println("A PersistenceException occurred while merging: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Boolean deliverOrders(int orderId, int employeeId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            if (orderId <= 0 || employeeId <= 0) {
                return false;
            }
            transaction.begin();


            Employee employee = entityManager.find(Employee.class, employeeId);
            Order order = entityManager.find(Order.class, orderId);

            if (order != null && employee != null && order.getPaymentStatus() == Status.Paid) {
                order.setEmployee(employee);
                order.setStatus(Status.Delivering);
                order.setUpdateTime(LocalDateTime.now());
                entityManager.merge(order);
                transaction.commit();
                return true;
            } else {
                return false;
            }
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
            if (orderId <= 0) {
                return false;
            }
            transaction.begin();


            Order order = entityManager.find(Order.class, orderId);

            if (order != null) {
                order.setStatus(Status.Delivered);

                entityManager.merge(order);
                transaction.commit();
                return true;
            } else {
                return false;
            }
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
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {

            if (orderId <= 0) {
                return false;
            }
            transaction.begin();

            Order order = entityManager.find(Order.class, orderId);

            if (order != null) {
                order.setStatus(Status.Cancelled);
                order.setPaymentStatus(Status.Refunded);
                order.setUpdateTime(LocalDateTime.now());
                entityManager.merge(order);
                transaction.commit();
                return true;
            } else {
                return false;
            }

        } catch (PersistenceException e) {
            transaction.rollback();
            System.out.println("A PersistenceException occurred while merging: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

    public void resetAutoIncrement(String tableName) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT=1").executeUpdate();

            transaction.commit();
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            System.err.println("An Exception occurred when AutoIncrementing the Product ID\n\t" + e);
            transaction.rollback();

        } finally {
            entityManager.close();
        }
    }

    public void resetAutoIncrement2(String tableName, int number) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Query updateIncrement = entityManager.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT=:number");
            updateIncrement.setParameter("number", number);
            updateIncrement.executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
            System.err.println("An Exception occurred when AutoIncrementing the Product ID\n\t" + e);
            transaction.rollback();

        } finally {
            entityManager.close();
        }
    }
}