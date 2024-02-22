package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cart_items")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
    @JoinColumn(name = "product_id", nullable = false)
    private int product_id;
    @Column(name = "cost")
    private double cost;
    @Column
    private int quantity;

    public CartItems() {

    }

    public CartItems(int id, Cart cart, int product_id, double cost, int quantity) {
        this.id = id;
        this.cart = cart;
        this.product_id = product_id;
        this.cost = cost;
        this.quantity = quantity;
    }

    public CartItems(Cart cart, int product_id, int quantity) {
        this.cart = cart;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public CartItems(Cart cart, int product_id, double cost, int quantity) {
        this.cart = cart;
        this.product_id = product_id;
        this.cost = cost;
        this.quantity = quantity;
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

    public int getProductID() {
        return product_id;
    }

    public void setProductID(int product_id) {
        this.product_id = product_id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItems{" +
                "id= " + id +
                ", cart= " + cart +
                ", productID= " + product_id +
                ", cost= " + cost +
                ", quantity= " + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItems cartItems = (CartItems) o;

        if (id != cartItems.id) return false;
        return Objects.equals(cart, cartItems.cart);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (cart != null ? cart.hashCode() : 0);
        return result;
    }
}