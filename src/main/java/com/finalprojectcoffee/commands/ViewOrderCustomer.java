package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.OrderItem;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderCustomer implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewOrderCustomer(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-order.jsp";

        HttpSession session = request.getSession(true);
        User loggedInUser  = (User) session.getAttribute("loggedInUser");

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            if(loggedInUser instanceof Customer){
                Customer activeCustomer = (Customer) loggedInUser;

                List<Order> ordersCustomer = orderRep.getAllOrdersByCustomerId(activeCustomer.getId());
                if(!ordersCustomer.isEmpty()){
                    session.setAttribute("ordersCustomer", ordersCustomer);
                } else {
                    session.setAttribute("emptyList", "Order list is empty");
                }

//                List<OrderItem> orderItemsCustomer = new ArrayList<>();
//                for(Order orderCustomer : ordersCustomer){
//                     orderItemsCustomer = orderRep.getOrderItemByOrderId(orderCustomer.getId());
//                }
//                if( orderItemsCustomer != null && !orderItemsCustomer.isEmpty()){
//                    session.setAttribute("orderItemsCustomer", orderItemsCustomer);
//                }
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing order list: " + e.getMessage());
        }
        return terminus;
    }
}
