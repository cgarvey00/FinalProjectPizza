package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * @author cgarvey00
 */
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ProductCategory category;

    @Column(name = "details", length = 500)
    private String details;

    @Column(name = "stock")
    private int stock;
    @Column(name = "price")
    private double price;

    @Column(name = "image")
    private String image;

    //Default Constructor
    public Product() {

    }

    /**
     * Constructs a new Product Object with the specified attributes
     *
     * @param name     the product name
     * @param category the category of the product
     * @param details  the description of the product
     * @param price    the product price, must be a non-negative value
     * @param stock    the quantity of the product stored must be a non-negative value
     * @param image    the image name of the product
     * @throws IllegalArgumentException If the stock or price is negative
     */

    //Parameterized Constructor Without ID
    public Product(String name, ProductCategory category, String details, double price, int stock, String image) {
        if (price < 0) {
            throw new IllegalArgumentException("You cannot set the price to a negative value, please try again");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("You cannot set the stock to a negative value, please try again");
        }

        this.name = name;
        this.category = category;
        this.details = details;
        this.stock = stock;
        this.price = price;
        this.image = image;
    }

    /**
     * Constructs a new Product Object with the specified attributes for a Product Object
     * containing an ID
     *
     * @param id       the products primary key specific to each product
     * @param name     the product name
     * @param category the category of the product
     * @param details  the description of the product
     * @param price    the product price, must be a non-negative value
     * @param stock    the quantity of the product stored must be a non-negative value
     * @param image    the image name of the product
     * @throws IllegalArgumentException If the stock or price is negative
     */

    //Parameterized Constructor With ID
    public Product(int id, String name, ProductCategory category, String details, double price, int stock, String image) {
        if (price < 0) {
            throw new IllegalArgumentException("You cannot set the price to a negative value, please try again");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("You cannot set the stock to a negative value, please try again");
        }
        this.id = id;
        this.name = name;
        this.category = category;
        this.details = details;
        this.stock = stock;
        this.price = price;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", details='" + details + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
