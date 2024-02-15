package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "employees")
@DiscriminatorValue("Employee")
public class Employee extends User {
    @Column(name = "salary")
    private Float salary;

    public Employee() {

    }

    public Employee(String username, String password, String phoneNumber, String email) {
        super(username, password, phoneNumber, email);
    }

    public Employee(String username, String password, String phoneNumber, String email, String image, Float salary) {
        super(username, password, phoneNumber, email, image);
        this.salary = salary;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                "} " + super.toString();
    }
}

