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

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
    @OneToOne
    @JoinColumn(name = "address", referencedColumnName = "id", nullable = false)
    private Address address;

    @Column(name = "balance", columnDefinition = "0.0")
    private double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", columnDefinition = "Pending")
    private Status paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "Pending")
    private Status status;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public Order() {
    }

    public Order(Cart cart, Customer customer, Employee employee, Address address, double balance, Status paymentStatus, Status status, LocalDateTime createTime, LocalDateTime updateTime) {
        this.cart = cart;
        this.customer = customer;
        this.employee = employee;
        this.address = address;
        this.balance = balance;
        this.paymentStatus = paymentStatus;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Order(int id, Cart cart, Customer customer, Employee employee, Address address, double balance, Status paymentStatus, Status status, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.cart = cart;
        this.customer = customer;
        this.employee = employee;
        this.address = address;
        this.balance = balance;
        this.paymentStatus = paymentStatus;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Status getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Status paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cart=" + cart +
                ", customer=" + customer +
                ", employee=" + employee +
                ", address=" + address +
                ", balance=" + balance +
                ", paymentStatus=" + paymentStatus +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
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
