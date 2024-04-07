package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
@DiscriminatorValue("Employee")
public class Employee extends User {
    @Column(name = "current_order_count")
    private int currentOrderCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.Available;

    public Employee() {

    }

    public Employee(int currentOrderCount, Status status) {
        this.currentOrderCount = currentOrderCount;
        this.status = status;
    }

    public Employee(String username, String password, String phoneNumber, String email) {
        super(username, password, phoneNumber, email);
    }

    public Employee(String username, String password, String phoneNumber, String email, int currentOrderCount) {
        super(username, password, phoneNumber, email);
        this.currentOrderCount = currentOrderCount;
    }

    public Employee(String username, String password, String phoneNumber, String email, int currentOrderCount, Status status) {
        super(username, password, phoneNumber, email);
        this.currentOrderCount = currentOrderCount;
        this.status = status;
    }

    public Employee(int id, String username, String password, String phoneNumber, String email, int currentOrderCount) {
        super(id, username, password, phoneNumber, email);
        this.currentOrderCount = currentOrderCount;
    }

    public Employee(int id, String username, String password, String phoneNumber, String email, int currentOrderCount, Status status) {
        super(id, username, password, phoneNumber, email);
        this.currentOrderCount = currentOrderCount;
        this.status = status;
    }

    public int getCurrentOrderCount() {
        return currentOrderCount;
    }

    public void setCurrentOrderCount(int currentOrderCount) {
        this.currentOrderCount = currentOrderCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "currentOrderCount=" + currentOrderCount +
                "} " + super.toString();
    }
}

