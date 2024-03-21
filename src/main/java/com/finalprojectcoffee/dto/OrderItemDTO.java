package com.finalprojectcoffee.dto;

public class OrderItemDTO {
    private int productId;
    private String Name;
    private int quantity;
    private double price;
    private double cost;

    public OrderItemDTO() {

    }

    public OrderItemDTO(int productId, String name, int quantity, double price, double cost) {
        this.productId = productId;
        Name = name;
        this.quantity = quantity;
        this.price = price;
        this.cost = cost;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "CartItemDTO{" +
                "productId=" + productId +
                ", Name='" + Name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", cost=" + cost +
                '}';
    }
}
