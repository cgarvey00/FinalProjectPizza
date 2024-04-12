package com.finalprojectcoffee.commands;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CommandFactory {
    public static Command getCommand(String action, HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        Command command = null;

        if (action != null) {
            switch (action) {
                case "home":
                    command = new ViewHome(request, response, factory);
                    break;
                case "login":
                    command = new Login(request, response, factory);
                    break;
                case "logout":
                    command = new Logout(request, response, factory);
                    break;
                case "register":
                    command = new Register(request, response, factory);
                    break;
                case "view-register":
                    command = new ViewRegister(request, response, factory);
                    break;
                case "view-login":
                    command = new ViewLogin(request, response, factory);
                    break;
                case "view-menu":
                    command = new ViewMenu(request, response, factory);
                    break;
                case "view-all-users":
                    command = new ViewAllUsers(request, response,factory);
                    break;
                case "view-all-employees":
                    command = new ViewAllEmployees(request, response, factory);
                    break;
                case "switch-employee-status":
                    command = new SwitchEmployeeStatus(request, response, factory);
                    break;
                case "set-all-employees-available":
                    command = new SetAllEmployeesAvailable(request, response, factory);
                    break;
                case "set-all-employees-unavailable":
                    command = new SetAllEmployeesUnavailable(request, response, factory);
                    break;
                case "to-update-employee":
                    command = new ToUpdateEmployee(request, response, factory);
                    break;
                case "view-products":
                    command = new ViewProducts(request, response, factory);
                    break;
                case "view-products-category":
                    command = new ViewProductByCategory(request, response, factory);
                    break;
                case "to-add-product":
                    command = new ToAddProduct(request, response, factory);
                    break;
                case "add-product":
                    command = new AddProduct(request, response, factory);
                    break;
                case "to-update-product":
                    command = new ToUpdateProduct(request, response, factory);
                    break;
                case "update-product":
                    command = new UpdateProduct(request, response, factory);
                    break;
                case "search-keyword":
                    command = new SearchKeyword(request, response, factory);
                    break;
                case "delete-product":
                    command = new DeleteProduct(request, response, factory);
                    break;
                case "admin-dashboard":
                    command = new ViewDashboard(request, response, factory);
                    break;
                case "view-user-profile":
                    command = new ViewUserProfile(request, response, factory);
                    break;
                case "update-user-profile":
                    command = new UpdateUserProfile(request, response, factory);
                    break;
                case "change-password":
                    command = new ChangePassword(request, response, factory);
                    break;
                case "delete-account":
                    command = new DeleteUser(request, response, factory);
                    break;
                case "add-address":
                    command = new AddAddress(request, response, factory);
                    break;
                case "to-update-address":
                    command = new ToUpdateAddress(request, response, factory);
                    break;
                case "update-default-address":
                    command = new UpdateDefaultAddress(request, response, factory);
                    break;
                case "update-address":
                    command = new UpdateAddress(request, response, factory);
                    break;
                case "delete-address":
                    command = new DeleteAddress(request, response, factory);
                    break;
                case "view-address":
                    command = new ViewAddress(request, response, factory);
                    break;
                case "add-to-cart":
                    command = new AddToCart(request, response, factory);
                    break;
                case "add-order":
                    command = new AddOrder(request, response, factory);
                    break;
                case "view-order":
                    command = new ViewOrder(request, response, factory);
                    break;
                case "view-orderList-customer":
                    command = new ViewOrderListCustomer(request, response, factory);
                    break;
                case "view-orderList-admin":
                    command = new ViewOrderListAdmin(request, response, factory);
                    break;
                case "update-address-in-order":
                    command = new UpdateAddressInOrder(request, response, factory);
                    break;
                case "update-employee-in-order":
                    command = new UpdateEmployeeInOrder(request, response, factory);
                    break;
                case "to-payment":
                    command = new ToPayment(request, response, factory);
                    break;
                case "pay-order":
                    command = new PayOrder(request, response, factory);
                    break;
                case "finish-order":
                    command = new FinishOrder(request, response, factory);
                    break;
                case "cancel-order":
                    command = new CancelOrder(request, response, factory);
                    break;
                case "update-quantity":
                    command = new UpdateQuantity(request, response, factory);
                    break;
                case "increment-quantity":
                    command = new IncrementQuantity(request, response, factory);
                    break;
                case "decrement-quantity":
                    command = new DecrementQuantity(request, response, factory);
                    break;
                case "delete-item":
                    command = new DeleteItem(request, response, factory);
                    break;
                case "clean-cart":
                    command = new CleanCart(request, response, factory);
                    break;
                default:
                    break;
            }
        }
        return command;
    }
}