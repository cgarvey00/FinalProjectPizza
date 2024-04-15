package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.List;

public class FilterOrderByDate implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public FilterOrderByDate(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus;
        HttpSession session = request.getSession(true);
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();

        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            List<Order> orderList = orderRep.filterOrderByDate(startDate, endDate, activeUserId);
            if(orderList != null && !orderList.isEmpty()){
                switch (activeUser.getUserType()) {
                    case "Admin":
                        session.setAttribute("orderList", orderList);
                        terminus = "view-order-admin.jsp";
                        break;
                    case "Customer":
                        session.setAttribute("orderListCustomer", orderList);
                        terminus = "view-order-customer.jsp";
                        break;
                    case "Employee":
                        terminus = "view-order-employee.jsp";
                        session.setAttribute("orderListEmployee", orderList);
                        break;
                    default:
                        terminus = "error.jsp";
                        break;
                }
            } else {
                session.setAttribute("errorMessage", "Order list is empty, please try again later.");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.out.println("An Exception occurred while filtering order by date: " + e.getMessage());
            terminus = "error.jsp";
        }
        return terminus;
    }
}