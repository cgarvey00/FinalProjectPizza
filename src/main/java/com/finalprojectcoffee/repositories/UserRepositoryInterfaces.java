package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.User;

import java.util.List;

public interface UserRepositoryInterfaces {
    User findUserById(int userId);
    User findUserByUsername(String username);
    Boolean findExistingEmail(String email);
    Boolean findExistingPhoneNumber(String phoneNumber);
    List<User> getAllUsers();
    List<Employee> getAllEmployees();
    Boolean switchEmployeeStatus(int employeeId);
    Boolean setAllEmployeeAvailable();
    Boolean setAllEmployeesUnavailable();
    Boolean addUser(User user);
    Boolean updateUser(int userId, String phoneNumber, String email);
    Boolean changePassword(int userId, String password);
    Boolean deleteUser(int userId);
    Boolean addAddress(int userId, Address address);
    Boolean updateAddress(Address address);
    Boolean setDefaultAddress(Address address);
    List<Address> getAddressesByUserId(int userId);
    Address getAddressById(int addressId);
    Boolean deleteAddress(int userId, int addressId);
    Address getDefaultAddress(int userId);
}
