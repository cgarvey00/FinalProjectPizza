package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
@DiscriminatorValue("Employee")
public class Employee extends User {
    @Column(name = "salary")
    private Float salary;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Enum status;

    public Employee() {

    }

    public Employee(String username, String password, String phoneNumber, String email) {
        super(username, password, phoneNumber, email);
    }

    public Employee(String username, String password, String phoneNumber, String email, Float salary) {
        super(username, password, phoneNumber, email);
        this.salary = salary;
    }

    public Employee(String username, String password, String phoneNumber, String email, Float salary, Enum status) {
        super(username, password, phoneNumber, email);
        this.salary = salary;
        this.status = status;
    }

    public Employee(int id, String username, String password, String phoneNumber, String email, String image, Float salary) {
        super(id, username, password, phoneNumber, email, image);
        this.salary = salary;
    }

    public Employee(int id, String username, String password, String phoneNumber, String email, String image, Float salary, Enum status) {
        super(id, username, password, phoneNumber, email, image);
        this.salary = salary;
        this.status = status;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                "} " + super.toString();
    }
}

