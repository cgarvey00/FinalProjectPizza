package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "customers")
public class Customer extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    public Customer() {

    }

    public Customer(String username, String password, String phoneNumber, String email,String type) {
        super(username, password, phoneNumber, email,type);
    }

    public Customer(int id, String username, String password, String phoneNumber, String email, String image, String type,Integer loyaltyPoints) {
        super(id, username, password, phoneNumber, email, image,type);
        this.loyaltyPoints = loyaltyPoints;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", loyaltyPoints=" + loyaltyPoints +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
