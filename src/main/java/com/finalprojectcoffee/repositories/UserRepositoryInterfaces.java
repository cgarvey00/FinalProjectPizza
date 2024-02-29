package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.User;

import java.util.List;

public interface UserRepositoryInterfaces {
    User findUserById(int userId);
    User findUserByUsername(String username);
    List<User> getAllUsers();
    Boolean addUser(User user);
    Boolean updateUser(int userId, String password, String phoneNumber, String email, String image);
    Boolean deleteUser(int userId);
    Boolean addAddress(int userId, Address address);
    Boolean updateAddress(int userId, String street, String town, String county, String eirCode);
    List<Address> getAddressesByUserId(int userId);
}
