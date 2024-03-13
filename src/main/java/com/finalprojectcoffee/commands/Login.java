package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import com.finalprojectcoffee.repositories.ProductRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import com.finalprojectcoffee.utils.JBCriptUtil;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
                User user = userRep.findUserByUsername(username);

                if (user != null) {
                    if (JBCriptUtil.checkPw(user.getPassword(), password)) {
                        switch (user.getUserType()) {
                            case "Customer":
                                terminus = "customer-home.jsp";

                                List<Address> addressList = userRep.getAddressesByUserId(user.getId());
                                boolean addressed;

                                if(addressList.isEmpty()){
                                    addressed = false;
                                    session.setAttribute("addressed", addressed);
                                } else {
                                    addressed = true;
                                    session.setAttribute("addressed", addressed);
                                }
                                break;
                            case "Employee":
                                terminus = "employee-home.jsp";
                                break;
                            case "Admin":
                                terminus = "admin-dashboard.jsp";

                                List<User> userList = userRep.getAllUsers();
                                session.setAttribute("userList", userList);

                                List<Order> orderList = orderRep.getAllOrders();
                                session.setAttribute("orderList", orderList);

                                List<Product> productList = productRep.getAllProducts();
                                session.setAttribute("productList", productList);
                                break;
                        }
                        session.setAttribute("loggedInUser", user);
                    } else {
                        String errorMessage = "Wrong password";
                        session.setAttribute("errorMessage", errorMessage);
                    }
                } else {
                    String errorMessage = "User doesn't exist";
                    session.setAttribute("errorMessage", errorMessage);
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
}