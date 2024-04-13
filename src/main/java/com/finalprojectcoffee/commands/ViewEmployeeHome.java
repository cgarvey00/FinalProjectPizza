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

public class ViewEmployeeHome implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewEmployeeHome(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "employee-home.jsp";
        HttpSession session = request.getSession(true);
        User activeUser = (User) session.getAttribute("loggedInUser");

        try {
            if(activeUser instanceof Employee){
                int employeeId = activeUser.getId();
                OrderRepositories orderRep = new OrderRepositories(factory);
                List<Order> currentOrdersForEmployee = orderRep.getCurrentOrdersForEmployee(employeeId);

                if(currentOrdersForEmployee != null && !currentOrdersForEmployee.isEmpty()){
                    session.setAttribute("currentOrdersForEmployee", currentOrdersForEmployee);
                } else {
                    session.setAttribute("errorMessage", "Order list is empty.");
                    terminus = "error.jsp";
                }
            }
        } catch (Exception e) {
            System.out.println("An Exception occurred while viewing employee home: " + e.getMessage());
            terminus = "error.jsp";
        }

        return terminus;
    }
}
