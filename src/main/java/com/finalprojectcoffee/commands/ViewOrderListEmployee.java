package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewOrderListEmployee implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewOrderListEmployee(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-order-employee.jsp";
        HttpSession session = request.getSession(true);
        User activeUser  = (User) session.getAttribute("loggedInUser");

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            if(activeUser instanceof Employee){
                Employee activeEmployee = (Employee) activeUser;
                List<Order> orderListEmployee = orderRep.getAllOrdersByEmployeeId(activeEmployee.getId());

                if(orderListEmployee != null && !orderListEmployee.isEmpty()){
                    session.setAttribute("orderListEmployee", orderListEmployee);
                } else {
                    session.setAttribute("errorMessage", "Order list is empty.");
                    terminus = "error.jsp";
                }
            }
        } catch (Exception e) {
            System.out.println("An Exception occurred while viewing order list: " + e.getMessage());
            terminus = "error.jsp";
        }

        return terminus;
    }
}
