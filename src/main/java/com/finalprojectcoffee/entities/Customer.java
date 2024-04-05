package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
@DiscriminatorValue("Customer")
public class Customer extends User{
    @Column(name = "loyalty_points", columnDefinition = "0")
    private Integer loyaltyPoints = 0;

    public Customer() {

    }

    public Customer(String username, String password, String phoneNumber, String email) {
        super(username, password, phoneNumber, email);
    }

    public Customer(int id, String username, String password, String phoneNumber, String email, Integer loyaltyPoints) {
        super(id, username, password, phoneNumber, email);
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