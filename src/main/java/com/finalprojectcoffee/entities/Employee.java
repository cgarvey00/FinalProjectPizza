package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "salary")
    private float salary;

    @Column(name = "fine")
    private float fine;

    public Employee() {

    }

    public Employee(int id, String username, String password, String phoneNumber, String email, String image, String token, int id1, User user, float salary, float fine) {
        super(id, username, password, phoneNumber, email, image, token);
        this.id = id1;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public float getFine() {
        return fine;
    }

    public void setFine(float fine) {
        this.fine = fine;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", user=" + user +
                ", salary=" + salary +
                ", fine=" + fine +
                '}';
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