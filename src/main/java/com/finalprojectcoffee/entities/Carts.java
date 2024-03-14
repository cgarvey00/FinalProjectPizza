package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "carts")
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_cost", nullable = false)
    private double total_cost;

    public Carts() {

    }

    public Carts(int id, Product product, Customer customer, int quantity,double total_cost) {
        this.id = id;
        this.product = product;
        this.customer = customer;
        this.quantity = quantity;
        this.total_cost=total_cost;
    }

    public Carts(Product product, Customer customer, int quantity,double total_cost) {
        this.product = product;
        this.customer = customer;
        this.quantity = quantity;
        this.total_cost=total_cost;
    }

    public Carts(int customerId, int productId, int quantity) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalCost() {
        return total_cost;
    }

    public void setTotalCost(double total_cost) {
        this.total_cost = total_cost;
    }

    @Override
    public String toString() {
        return "Carts{" +
                "id=" + id +
                ", product=" + product +
                ", customer=" + customer +
                ", quantity=" + quantity +
                ", total-cost="+total_cost+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carts)) return false;

        Carts carts = (Carts) o;

        return id == carts.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
