package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "customers")
@DiscriminatorValue("Customer")
public class Customer extends User{
    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    public Customer() {

    }

    public Customer(String username, String password, String phoneNumber, String email) {
        super(username, password, phoneNumber, email);
    }

    public Customer(int id, String username, String password, String phoneNumber, String email, String image, Integer loyaltyPoints) {
        super(id, username, password, phoneNumber, email, image);
        this.loyaltyPoints = loyaltyPoints;
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
                "loyaltyPoints=" + loyaltyPoints +
                "} " + super.toString();
    }
}
