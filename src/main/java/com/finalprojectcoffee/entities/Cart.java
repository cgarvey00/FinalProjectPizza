package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne
    @JoinColumn(name = "orders", nullable = false)
    private Order order;

    @OneToMany(mappedBy = "carts",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> cartItems;

    @Column(name = "total_cost", columnDefinition = "0.0")
    private double totalCost;

    public Cart() {

    }

    public Cart(Order order, List<CartItem> cartItems, double totalCost) {
        this.order = order;
        this.cartItems = cartItems;
        this.totalCost = totalCost;
    }

    public Cart(int id, Order order, List<CartItem> cartItems, double totalCost) {
        this.id = id;
        this.order = order;
        this.cartItems = cartItems;
        this.totalCost = totalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", order=" + order +
                ", cartItems=" + cartItems +
                ", totalCost=" + totalCost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id == cart.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}