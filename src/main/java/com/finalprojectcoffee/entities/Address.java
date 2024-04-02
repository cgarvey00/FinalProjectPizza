package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_default")
    private int isDefault;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "street")
    private String street;

    @Column(name = "town")
    private String town;

    @Column(name = "county")
    private String county;

    @Column(name = "eir_code")
    private String eirCode;

    public Address() {
    }

    public Address(String street, String town, String county, String eirCode) {
        this.street = street;
        this.town = town;
        this.county = county;
        this.eirCode = eirCode;
    }

    public Address(int isDefault, User user, String street, String town, String county, String eirCode) {
        this.isDefault = isDefault;
        this.user = user;
        this.street = street;
        this.town = town;
        this.county = county;
        this.eirCode = eirCode;
    }

    public Address(int id, int isDefault, User user, String street, String town, String county, String eirCode) {
        this.id = id;
        this.isDefault = isDefault;
        this.user = user;
        this.street = street;
        this.town = town;
        this.county = county;
        this.eirCode = eirCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getEirCode() {
        return eirCode;
    }

    public void setEirCode(String eirCode) {
        this.eirCode = eirCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", isDefault=" + isDefault +
                ", user=" + user +
                ", street='" + street + '\'' +
                ", town='" + town + '\'' +
                ", county='" + county + '\'' +
                ", eirCode='" + eirCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
