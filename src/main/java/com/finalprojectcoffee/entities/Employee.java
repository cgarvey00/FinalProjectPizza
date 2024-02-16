package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "employees")
@DiscriminatorColumn(name="type",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Employee")
public class Employee extends User {
    @Column(name = "salary")
    private Float salary;

    public Employee() {

    }

    public Employee(String username, String password, String phoneNumber, String email,String type) {
        super(username, password, phoneNumber, email,type);
    }

    public Employee(String username, String password, String phoneNumber, String email, String image,String type, Float salary) {
        super(username, password, phoneNumber, email, image,type);
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

