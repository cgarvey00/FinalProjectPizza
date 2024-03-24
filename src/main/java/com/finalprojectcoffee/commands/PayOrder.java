package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class PayOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public PayOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus;

        HttpSession session = request.getSession(true);
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();

        double amount;
        try {
            amount = Double.parseDouble(request.getParameter("amount"));
        } catch (NumberFormatException  e) {
            session.setAttribute("pmeMessage", "Please enter a valid number for the amount, please try again");
            return "payment.jsp";
        }

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            UserRepositories userRep = new UserRepositories(factory);
            Order order = orderRep.findOrderById(orderId);

            if(activeUser instanceof Customer){
                Customer activeCustomer = (Customer) activeUser;
                List<Address> addressList = userRep.getAddressesByUserId(activeUserId);
                List<Order> orderListCustomer = orderRep.getAllOrdersByCustomerId(activeCustomer.getId());

                Boolean isPaid = orderRep.payOrder(order.getId());
                if(isPaid){
                    session.setAttribute("orderListCustomer", orderListCustomer);
                    session.setAttribute("addressList", addressList);
                    session.setAttribute("posMessage", "Pay successfully!");
                    terminus = "payment.jsp";
                } else {
                    session.setAttribute("errorMessage", "Failed to go to payment page. Please try again later.");
                    terminus = "error.jsp";
                }

            } else {
                session.setAttribute("errorMessage", "Please login first.");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while paying order: " + e.getMessage());
            session.setAttribute("errorMessage", "Something went wrong");
            return "error.jsp";
        }

        return terminus;
    }
}
