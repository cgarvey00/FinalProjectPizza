package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.User;

public class UserFactory {

    public static User createUser(String userType, String username, String password, String email, String phoneNumber) {
        switch (userType) {
            case "customer":
                return new Customer(username, password, email, phoneNumber);

            case "employee":
                return new Employee(username, password, email, phoneNumber);

            default:
                return new User(username, password, email, phoneNumber);
        }
    }
}

