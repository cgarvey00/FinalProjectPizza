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
        User activeUser  = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            UserRepositories userRep = new UserRepositories(factory);

            if(activeUser instanceof Customer){
                Customer activeCustomer = (Customer) activeUser;
                List<Address> addressList = userRep.getAddressesByUserId(activeUserId);
                List<Order> orderListCustomer = orderRep.getAllOrdersByCustomerId(activeCustomer.getId());

                if(!orderListCustomer.isEmpty()){
                    session.setAttribute("orderListCustomer", orderListCustomer);
                    session.setAttribute("addressList", addressList);
                } else {
                    session.setAttribute("errorMessage", "Order list is empty");
                    terminus = "error.jsp";
                }
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing order list: " + e.getMessage());
        }
        return terminus;
    }
}