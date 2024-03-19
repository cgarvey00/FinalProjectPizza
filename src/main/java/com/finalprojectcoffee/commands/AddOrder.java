package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.*;
import com.finalprojectcoffee.repositories.CartsRepositories;
import com.finalprojectcoffee.repositories.OrderRepositories;
import com.finalprojectcoffee.utils.PaymentUtil;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AddOrder implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public AddOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "customer-home.jsp";
        HttpSession session = request.getSession(true);

        int addressID = Integer.parseInt(request.getParameter("address"));
        String paymentMethod = request.getParameter("method");
        String cardNumber = request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expdate");
        double total = Double.parseDouble(request.getParameter("total"));
        String cvv = request.getParameter("cvv");
        try {
            if (PaymentUtil.validatePaymentInfo(paymentMethod, cardNumber, expiryDate, cvv)) {
                User loggedInUser = (User) session.getAttribute("loggedInUser");
                CartsRepositories cartRep = new CartsRepositories(factory);

                List<Carts> cartItems = cartRep.getCartsByCustomerId(loggedInUser.getId());

                if (loggedInUser != null && cartItems != null && !cartItems.isEmpty()) {
                    OrderRepositories orderRep = new OrderRepositories(factory);
                    Order o = orderRep.addOrder(loggedInUser.getId(), addressID, cartItems);

                    if (o != null) {
                        session.setAttribute("ap3-message", "Order Added successfully");
                        cartRep.clearCart(loggedInUser.getId());
                        boolean payOrder = orderRep.payOrder(o.getId(), total);
                        if (payOrder) {
                            session.setAttribute("ap2-message", "Order Paid successfully");

                            boolean accepted = orderRep.acceptOrders(o.getId());

                            if (accepted) {
                                session.setAttribute("ap1-message", "Order has been successfully accepted, await for delivery");
                                terminus = "customer-home.jsp";
                            }
                        }
                    } else {
                        session.setAttribute("aps-message", "Payment Failed");
                        terminus = "view-cart.jsp";
                    }

                }

            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while adding products: " + e.getMessage());
        }
        return terminus;


    }
}
