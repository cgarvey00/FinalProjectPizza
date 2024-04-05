package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ToUpdateEmployee implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ToUpdateEmployee(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "employee-update.jsp";
        HttpSession session = request.getSession(true);
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));

        try {
            UserRepositories userRep = new UserRepositories(factory);

            User user = userRep.findUserById(employeeId);
            if(user instanceof Employee){
                Employee employee = (Employee) user;
                session.setAttribute("employee", employee);
            }
        } catch (Exception e) {
            System.out.println("An Excpetion occurred while updating employee: " + e.getMessage());
            return "error.jsp";
        }

        return terminus;
    }
}
