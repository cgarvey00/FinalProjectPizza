package com.finalprojectcoffee.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Yutang
 */
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "temp_address_id", referencedColumnName = "id")
    private TemporaryAddress temporaryAddress;

    @Column(name = "balance", columnDefinition = "0.0")
    private double balance;
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, columnDefinition = "Pending")
    private Enum paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Enum status;

    @Column(name = "create_date")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "overdue_time")
    private LocalDateTime overdueTime;

    public Order() {
    }

    public Order(Cart cart, Customer customer, Employee employee, TemporaryAddress temporaryAddress, double balance, String paymentMethod, Enum paymentStatus, Enum status, LocalDateTime createTime, LocalDateTime updateTime, LocalDateTime overdueTime) {
        this.cart = cart;
        this.customer = customer;
        this.employee = employee;
        this.temporaryAddress = temporaryAddress;
        this.balance = balance;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.overdueTime = overdueTime;
    }

    public Order(int id, Cart cart, Customer customer, Employee employee, TemporaryAddress temporaryAddress, double balance, String paymentMethod, Enum paymentStatus, Enum status, LocalDateTime createTime, LocalDateTime updateTime, LocalDateTime overdueTime) {
        this.id = id;
        this.cart = cart;
        this.customer = customer;
        this.employee = employee;
        this.temporaryAddress = temporaryAddress;
        this.balance = balance;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.overdueTime = overdueTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public TemporaryAddress getTemporaryAddress() {
        return temporaryAddress;
    }

    public void setTemporaryAddress(TemporaryAddress temporaryAddress) {
        this.temporaryAddress = temporaryAddress;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Enum getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Enum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(LocalDateTime overdueTime) {
        this.overdueTime = overdueTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cart=" + cart +
                ", customer=" + customer +
                ", employee=" + employee +
                ", temporaryAddress=" + temporaryAddress +
                ", balance=" + balance +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus=" + paymentStatus +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", overdueTime=" + overdueTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
