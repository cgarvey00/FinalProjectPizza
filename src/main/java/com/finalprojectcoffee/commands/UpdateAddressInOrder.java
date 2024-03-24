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

public class UpdateAddressInOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public UpdateAddressInOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
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
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int addressId = Integer.parseInt(request.getParameter("selectedAddressId"));

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            UserRepositories userRep = new UserRepositories(factory);

            if (activeUser instanceof Customer) {

                Customer activeCustomer = (Customer) activeUser;
                Boolean isUpdated = orderRep.updateAddressInOrder(orderId, addressId);
                if (isUpdated) {
                    List<Address> addressList = userRep.getAddressesByUserId(activeUserId);
                    List<Order> orderListCustomer = orderRep.getAllOrdersByCustomerId(activeCustomer.getId());
                    session.setAttribute("orderListCustomer", orderListCustomer);
                    session.setAttribute("addressList", addressList);
                } else {
                    session.setAttribute("errorMessage", "Failed to update address in order. Please try again later.\"");
                    terminus = "error.jsp";
                }
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while updating address in order: " + e.getMessage());
            session.setAttribute("errorMessage", "Something went wrong");
            terminus = "error.jsp";
        }

        return terminus;
    }
}
