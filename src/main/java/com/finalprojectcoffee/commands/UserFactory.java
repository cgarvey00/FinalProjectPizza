package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.User;

public class UserFactory {

    public static User createUser(String userType, String username, String password, String email, String phoneNumber) {
        switch (userType) {
            case "Customer":
                return new Customer(username, password,phoneNumber,email,userType);

            case "Employee":
                return new Employee(username, password, phoneNumber,email,userType);

            default:
                return new User(username, password, phoneNumber,email,userType);
        }
    }
}

