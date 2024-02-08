package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "employees")
@DiscriminatorValue("Employee")
public class Employee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "salary")
    private Float salary;

    @Column(name = "fine")
    private Float fine;

    public Employee() {

    }

    public Employee(String username, String password, String phoneNumber, String email) {
        super(username, password, phoneNumber, email);
    }

    public Employee(int id, String username, String password, String phoneNumber, String email, String image, int id1, Float salary, Float fine) {
        super(id, username, password, phoneNumber, email, image);
        this.id = id1;
        this.salary = salary;
        this.fine = fine;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Float getFine() {
        return fine;
    }

    public void setFine(Float fine) {
        this.fine = fine;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", salary=" + salary +
                ", fine=" + fine +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}