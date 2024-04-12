package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class SwitchEmployeeStatus implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public SwitchEmployeeStatus(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-all-employees.jsp";
        HttpSession session = request.getSession(true);

        try {
            int employeeId = Integer.parseInt(request.getParameter("employeeId"));
            UserRepositories userRep = new UserRepositories(factory);

            Boolean isSwitched = userRep.switchEmployeeStatus(employeeId);
            if (isSwitched) {
                List<Employee> employeeList = userRep.getAllEmployees();
                session.setAttribute("employeeList", employeeList);
            } else {
                session.setAttribute("errorMessage", "Failed to switch employee's status");
                terminus = "error.jsp";
            }
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Employee doesn't exist");
            terminus = "error.jsp";
        } catch (Exception e){
            System.out.println("An Exception occurred while switching employee's status: " + e.getMessage());
            terminus = "error.jsp";
        }

        return terminus;
    }
}
