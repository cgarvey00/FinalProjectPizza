package com.finalprojectcoffee.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "temp_addresses")
public class TempAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "street")
    private String street;

    @Column(name = "town")
    private String town;

    @Column(name = "county")
    private String county;

    @Column(name = "eir_code")
    private String eirCode;

    public TempAddress() {
    }

    public TempAddress(String street, String town, String county, String eirCode) {
        this.street = street;
        this.town = town;
        this.county = county;
        this.eirCode = eirCode;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "TempAddress{" +
                "id=" + id +
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
        TempAddress that = (TempAddress) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
