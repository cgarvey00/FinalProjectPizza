package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import com.finalprojectcoffee.utils.JBCriptUtil;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Login implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public Login(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "login.jsp";
        HttpSession session = request.getSession(true);
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            EntityManager entityManager = factory.createEntityManager();
            try {
                UserRepositories UserRep = new UserRepositories(factory);
                User user = UserRep.findUserByUsername(username);
                if (user != null) {
                    if (JBCriptUtil.checkPw(user.getPassword(), password)) {
                        //Obtaining the User Type
                        DiscriminatorValue discriminatorValue = user.getClass().getAnnotation(DiscriminatorValue.class);
                        String uType;
                        if (discriminatorValue != null) {
                            //Setting the User Type as a String
                            uType = discriminatorValue.value();
                            session.setAttribute("userRole", uType);
                            session.setAttribute("loggedInUser", user);

                            switch (uType) {
                                case "Admin":
                                    terminus = new UserPage("admin-dashboard",request,response,factory).execute();
                                    break;
                                case "Customer":
                                    terminus = new UserPage("customer-dashboard",request,response,factory).execute();
                                    break;
                                case "Employee":
                                    terminus = "employee-page.jsp";
                                    break;
                                default:
                                    String errorMessage = "You may not be registered, try register if possible";
                                    session.setAttribute("errorMessage", errorMessage);
                            }
                        } else {
                            String errorMessage = "You may not be registered, try register if possible";
                            session.setAttribute("errorMessage", errorMessage);
                        }
                    } else {
                        String errorMessage = "Wrong password";
                        session.setAttribute("errorMessage", errorMessage);
                    }
                } else {
                    String errorMessage = "User doesn't exist";
                    session.setAttribute("errorMessage", errorMessage);
                }
            } finally {
                entityManager.close();
            }
        } else {
            String errorMessage = "Register for Account";
            session.setAttribute("errorMessage", errorMessage);
        }
        return terminus;
    }
}
