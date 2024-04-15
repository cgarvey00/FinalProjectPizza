package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.repositories.OrderRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class UpdateEmployeeInOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public UpdateEmployeeInOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-order-today-admin.jsp";

        HttpSession session = request.getSession(true);
        int employeeId = Integer.parseInt(request.getParameter("selectedEmployeeId"));
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            UserRepositories userRep = new UserRepositories(factory);

            Boolean isUpdated = orderRep.updateEmployeeInOrder(orderId, employeeId);
            if (isUpdated) {
                List<Order> orderList = orderRep.getAllOrders();
                List<Employee> employeeList = userRep.getAllEmployees();
                if(!orderList.isEmpty()){
                    session.setAttribute("orderList", orderList);
                    session.setAttribute("employeeList", employeeList);
                } else {
                    session.setAttribute("errorMessage", "Order list is empty");
                    terminus = "error.jsp";
                }
            } else {
                session.setAttribute("errorMessage", "Failed to update employee in order");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.out.println("An Exception occurred whlie updating employee in order: " + e.getMessage());
            terminus = "error.jsp";
        }

        return terminus;
    }
}
