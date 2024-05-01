package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.*;
import com.finalprojectcoffee.repositories.OrderRepositories;
import com.finalprojectcoffee.repositories.ProductRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import com.finalprojectcoffee.utils.JBCryptUtil;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Login implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public Login(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "login.jsp";
        HttpSession session = request.getSession(true);
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            try {
                UserRepositories userRep = new UserRepositories(factory);
                OrderRepositories orderRep = new OrderRepositories(factory);
                ProductRepositories productRep = new ProductRepositories(factory);
                User user = userRep.findUserByUsername(username);

                if (user != null) {
                    if (JBCryptUtil.checkPw(user.getPassword(), password)) {
                        List<Product> productList = productRep.getAllProducts();
                        switch (user.getUserType()) {
                            case "Customer":
                                terminus = "customer-home.jsp";

                                List<Address> addressList = userRep.getAddressesByUserId(user.getId());

                                if(addressList.isEmpty()){
                                    session.setAttribute("addressed", false);
                                } else {
                                    session.setAttribute("addressed", true);
                                }

                                List<Product> recommendedProducts = new ArrayList<>();

                                Product chicagoDD = productRep.findProductByID(12);
                                Product family = productRep.findProductByID(11);
                                Product strawberryCake = productRep.findProductByID(15);
                                recommendedProducts.add(chicagoDD);
                                recommendedProducts.add(family);
                                recommendedProducts.add(strawberryCake);

                                Map<ProductCategory, List<Product>> productListByCategory = new LinkedHashMap<>();
                                for (Product product : productList) {
                                    productListByCategory
                                            .computeIfAbsent(product.getCategory(), k -> new ArrayList<>())
                                            .add(product);
                                }
                                session.removeAttribute("order");
                                if (!productList.isEmpty()) {
                                    session.setAttribute("productListByCategory", productListByCategory);
                                }
                                session.setAttribute("recommendedProducts", recommendedProducts);
                                session.setAttribute("userType", "customer");
                                break;
                            case "Employee":
                                int employeeId = user.getId();
                                List<Order> currentOrdersForEmployee = orderRep.getCurrentOrdersForEmployee(employeeId);

                                session.setAttribute("currentOrdersForEmployee", currentOrdersForEmployee);
                                session.setAttribute("userType", "employee");
                                terminus = "employee-home.jsp";
                                break;
                            case "Admin":
                                terminus = "admin-dashboard.jsp";

                                List<User> userList = userRep.getAllUsers();
                                session.setAttribute("userList", userList);

                                List<Employee> employeeList = userRep.getAllEmployees();
                                session.setAttribute("employeeList", employeeList);

                                List<Order> orderListByToday = orderRep.getAllOrdersToday();
                                session.setAttribute("orderListByToday", orderListByToday);

                                session.setAttribute("productList", productList);
                                session.setAttribute("userType", "admin");
                                break;
                        }
                        session.setAttribute("loggedInUser", user);
                    } else {
                        String loginMessage = "Wrong password";
                        session.setAttribute("loginMessage", loginMessage);
                    }
                } else {
                    String loginMessage = "User doesn't exist";
                    session.setAttribute("loginMessage", loginMessage);
                }
            } catch (Exception e) {
                System.err.println("An Exception occurred while logging: " + e.getMessage());
            }
        } else {
            String loginMessage = "Register for Account";
            session.setAttribute("loginMessage", loginMessage);
        }
        return terminus;
    }
}