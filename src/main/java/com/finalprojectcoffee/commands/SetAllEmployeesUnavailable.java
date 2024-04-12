package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class SetAllEmployeesUnavailable implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public SetAllEmployeesUnavailable(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-all-employees.jsp";
        HttpSession session = request.getSession(true);

        try {
            UserRepositories userRep = new UserRepositories(factory);

            Boolean isSet = userRep.setAllEmployeesUnavailable();
            if (isSet) {
                List<Employee> employeeList = userRep.getAllEmployees();
                session.setAttribute("employeeList", employeeList);
            } else {
                session.setAttribute("errorMessage", "Failed to setting all employees unavailable, please try again later");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.out.println("An Exception occurred while setting all employees unavailable: " + e.getMessage());
            terminus = "error.jsp";
        }
        return terminus;
    }
}
