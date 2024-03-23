package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewOrderListCustomer implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewOrderListCustomer(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-order-customer.jsp";

        HttpSession session = request.getSession(true);
        User loggedInUser  = (User) session.getAttribute("loggedInUser");

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            if(loggedInUser instanceof Customer){
                Customer activeCustomer = (Customer) loggedInUser;

                List<Order> orderListCustomer = orderRep.getAllOrdersByCustomerId(activeCustomer.getId());
                if(!orderListCustomer.isEmpty()){
                    session.setAttribute("orderListCustomer", orderListCustomer);
                } else {
                    session.setAttribute("emptyList", "Order list is empty");
                }
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing order list: " + e.getMessage());
        }
        return terminus;
    }
}
