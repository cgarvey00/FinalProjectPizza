package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "order_items")
public class OrdersItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order orders;
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "cost")
    private double cost;

    public OrdersItem() {

    }

    public OrdersItem(Order orders, Product product, int quantity, double cost) {
        this.orders = orders;
        this.product = product;
        this.quantity = quantity;
        this.cost = cost;
    }

    public OrdersItem(int id, Order orders, Product product, int quantity, double cost) {
        this.id = id;
        this.orders = orders;
        this.product = product;
        this.quantity = quantity;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return orders;
    }

    public void setOrder(Order orders) {
        this.orders =orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }



    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", Order=" + orders +
                ", product=" + product +
                ", quantity=" + quantity +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order orderItem = (Order) o;
        return id == orderItem.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
