package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.*;
import com.finalprojectcoffee.repositories.*;
import com.finalprojectcoffee.utils.JBCriptUtil;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

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
                ReviewRepository reviewRep = new ReviewRepository(factory);
                CartsRepositories cartRep = new CartsRepositories(factory);
                User user = userRep.findUserByUsername(username);

                if (user != null) {
                    if (JBCriptUtil.checkPw(user.getPassword(), password)) {
                        switch (user.getUserType()) {
                            case "Customer":
                                terminus = "customer-home.jsp";
                                List<Carts> cartList = cartRep.getCartsByCustomerId(user.getId());
                                List<Address> addressList = userRep.getAddressesByUserId(user.getId());

                                session.setAttribute("cartLists", cartList.size());
                                if (addressList.isEmpty()) {
                                    session.setAttribute("addressed", "Fill in your address");
                                }
                                break;
                            case "Employee":
                                List<User> userList1 = userRep.getAllUsers();
                                terminus = "employee-home.jsp";
                                session.setAttribute("eUserList", userList1);

                                List<Order> eOrderList = orderRep.getAllOrders();
                                session.setAttribute("eOrderList", eOrderList);

                                List<Order> freeOrders = getFreeOrders(eOrderList);

                                if (freeOrders.equals(null)) {
                                    freeOrders = new ArrayList<>();
                                }
                                session.setAttribute("freeOrders", freeOrders);

                                List<Product> productList1 = productRep.getAllProducts();
                                session.setAttribute("productList", productList1);
                                break;
                            case "Admin":
                                terminus = "admin-dashboard.jsp";

                                List<User> userList = userRep.getAllUsers();
                                session.setAttribute("userList", userList);

                                List<Order> orderList = orderRep.getAllOrders();
                                session.setAttribute("orderList", orderList);

                                //
                                List<Order> pendingOrders = getPendingOrders(orderList);
                                session.setAttribute("pendingOrders", pendingOrders);

                                List<Review> reviewList = reviewRep.getAllReviews();
                                session.setAttribute("allReviews", reviewList);

                                List<Product> productList = productRep.getAllProducts();
                                session.setAttribute("productList", productList);
                                break;
                        }
                        session.setAttribute("loggedInUser", user);
//                        session.removeAttribute("errorMessage1");
                    } else {
                        String errorMessage = "Wrong password";
                        session.setAttribute("errorMessage1", errorMessage);
                    }
                } else {
                    String errorMessage = "User Account does not exist";
                    session.setAttribute("errorMessage2", errorMessage);
                }
            } catch (Exception e) {
                System.err.println("An Exception occurred while logging: " + e.getMessage());
            }
        } else {
            String errorMessage = "Register for Account";
            session.setAttribute("errorMessage", errorMessage);
        }
        return terminus;
    }

    private static List<Order> getPendingOrders(List<Order> orderList) {
        List<Order> newList = new ArrayList<>();
        for (Order o : orderList) {
            if (o.getStatus().equals(Status.Pending)) {
                newList.add(o);
            }

        }
        return newList;
    }

    private static List<Order> getFreeOrders(List<Order> orderList) {
        List<Order> newList = new ArrayList<>();
        for (Order o : orderList) {
            if (o.getEmployee() == (null)) {
                newList.add(o);
            }

        }
        return newList;
    }
}