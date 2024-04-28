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
                    case "search-keyword":
                        command = new SearchKeyword(request, response, factory);
                        break;
                    case "view-register":
                        command = new ViewRegister(request, response, factory);
                        break;
                    case "view-login":
                        command = new ViewLogin(request, response, factory);
                        break;
                    case "view-search":
                        command = new ViewSearch(request, response, factory);
                        break;
                    case "view-landing-menu":
                        command = new ViewProductsLanding(request, response, factory);
                        break;
                    case "landing-view-calories":
                        command = new ViewLandingCalories(request, response, factory);
                        break;
                    case "view-menu":
                        command = new ViewMenu(request, response, factory);
                        break;
                    case "view-order-history":
                        command = new EmployeeViewFinishedOrders(request, response, factory);
                        break;
                    case "viewcurrent-emp-orders":
                        command = new EmployeeOrderList(request, response, factory);
                        break;
                    case "view-order-customer":
                        command = new ViewOrderCustomer(request, response, factory);
                        break;
                    case "emp-view-address":
                        command = new ViewAddressDetailsEmployee(request, response, factory);
                        break;
                    case "emp-view-address2":
                        command = new ViewAddressDetailsEmployeeDeliveryList(request, response, factory);
                        break;
                    case "view-order-employee":
                        command = new ViewOrderEmployee(request, response, factory);
                        break;
                    case "view-admin-orders":
                        command = new ViewAdminOrders(request, response, factory);
                        break;
                    case "view-admin-users":
                        command = new ViewAdminUsers(request, response, factory);
                        break;
                    case "get-order-details":
                        command = new ViewOrderDetailsEmployee(request, response, factory);
                        break;
                    case "finish-order":
                        command = new DeliverOrderToCustomer(request, response, factory);
                        break;
                    case "view-order-details":
                        command = new ViewOrderItems(request, response, factory);
                        break;
                    case "view-all-users":
                        command = new ViewAllUsers(request, response, factory);
                        break;
                    case "view-all-reviews-admin":
                        command = new AdminPDFReview(request, response, factory);
                        break;
                    case "view-review":
                        command = new ViewReview(request, response, factory);
                        break;
                    case "add-review":
                        command = new AddReview(request, response, factory);
                        break;
                    case "view-products-category":
                        command = new ViewProductByCategory(request, response, factory);
                        break;
                    case "add-product":
                        command = new AddProduct(request, response, factory);
                        break;
                    case "view-cart":
                        command = new ViewCart(request, response, factory);
                        break;
                    case "add-to-cart":
                        command = new AddCart(request, response, factory);
                        break;
                    case "view-products":
                        command = new ViewStock(request, response, factory);
                        break;
                    case "delete-cart-item":
                        command = new DeleteCart(request, response, factory);
                        break;
                    case "update-cart":
                        command = new UpdateCart(request, response, factory);
                        break;
                    case "update-product":
                        command = new UpdateProduct(request, response, factory);
                        break;
                    case "view-update-product":
                        command = new ViewUpdateProduct(request, response, factory);
                        break;
                    case "view-changepassword":
                        command = new ViewChangePassword(request, response, factory);
                        break;
                    case "change-password":
                        command = new ChangePassword(request, response, factory);
                        break;
                    case "delete-product":
                        command = new DeleteProduct(request, response, factory);
                        break;
                    case "update-profile":
                        command = new UpdateUserProfile(request, response, factory);
                        break;
                    case "admin-dashboard":
                        command = new ViewDashboard(request, response, factory);
                        break;
                    case "view-user-profile":
                        command = new ViewUserProfile(request, response, factory);
                        break;
                    case "deliver-order":
                        command = new AcceptOrderEmployee(request, response, factory);
                        break;
                    case "view-checkout":
                        command = new ViewPayment(request, response, factory);
                        break;
                    case "add-address":
                        command = new AddAddress(request, response, factory);
                        break;
                    case "view-address":
                        command = new ViewAddress(request, response, factory);
                        break;
                    case "view-all-orders":
                        command = new ViewAllOrders(request, response, factory);
                        break;
                    case "add-order":
                        command = new AddOrder(request, response, factory);
                        break;
                    case "clear-cart":
                        command = new CleanCart(request, response, factory);
                        break;
                    case "generate-popular-product-pdf":
                        command = new MostPopularProduct(request, response, factory);
                        break;
                    case "customer-page":
                        command = new CustomerPage(request, response, factory);
                        break;
                    case "employee-page":
                        command = new EmployeePage(request, response, factory);
                        break;
                    default:
                        break;
                }
            }
            return command;
        }
    }


