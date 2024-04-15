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

public class ViewOrderListAdminToday implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewOrderListAdminToday(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-today-order-admin.jsp";
        HttpSession session = request.getSession(true);

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            UserRepositories userRep = new UserRepositories(factory);

            List<Order> orderListByToday = orderRep.getAllOrdersToday();
            List<Employee> employeeList = userRep.getAllEmployees();
            if(orderListByToday != null){
                session.setAttribute("orderListByToday", orderListByToday);
                session.setAttribute("employeeList", employeeList);
            } else {
                session.setAttribute("errorMessage", "Order list is empty");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.out.println("An Exception occurred while getting order list: " + e.getMessage());
            terminus = "error.jsp";
        }

        return terminus;
    }
}
