package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @Column(name = "cart_id", nullable = false)
    private Cart cart;
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "cost")
    private double cost;

    public CartItem() {

    }

    public CartItem(Cart cart, Product product, int quantity, double cost) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.cost = cost;
    }

    public CartItem(int id, Cart cart, Product product, int quantity, double cost) {
        this.id = id;
        this.cart = cart;
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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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
        return "CartItem{" +
                "id=" + id +
                ", cart=" + cart +
                ", product=" + product +
                ", quantity=" + quantity +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return id == cartItem.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}