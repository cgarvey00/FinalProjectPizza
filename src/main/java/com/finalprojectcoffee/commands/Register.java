package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import com.finalprojectcoffee.utils.EmailUtil;
import com.finalprojectcoffee.utils.JBCriptUtil;
import com.finalprojectcoffee.utils.PhoneNumberUtil;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Register implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public Register(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "register.jsp";
        HttpSession session = request.getSession(true);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String userType = "Customer";

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty() && passwordConfirmation != null && !passwordConfirmation.isEmpty() && email != null && !email.isEmpty() && phoneNumber != null && !phoneNumber.isEmpty()) {
            try {
                UserRepositories userRep = new UserRepositories(factory);
                User user = userRep.findUserByUsername(username);
                User userEmail = userRep.findUserByEmail(email);
                if (user != null && user.getUsername().equals(username)) {
                    session.setAttribute("umsg", "Username or Email already in Use, please try again");
                    return terminus;
                }

                if (userEmail != null && userEmail.getEmail().equals(email)) {
                    session.setAttribute("umsg", "Email Already in Use Please try again");
                    return terminus;
                }

                if (!JBCriptUtil.validatePassword(password)) {
                    session.setAttribute("pwvmsg", "Password format error");
                    return terminus;
                }

                if (!password.equals(passwordConfirmation)) {
                    session.setAttribute("pwcmsg", "Password inconsistency");
                    return terminus;
                }

                if (!EmailUtil.validateEmail(email)) {
                    session.setAttribute("emsg", "Email format error");
                    return terminus;
                }

                if (!PhoneNumberUtil.validationPhoneNumber(phoneNumber)) {
                    session.setAttribute("pnmsg", "Phone number format error");
                    return terminus;
                }

                if (user == null && JBCriptUtil.validatePassword(password) &&
                        password.equals(passwordConfirmation) && EmailUtil.validateEmail(email) &&
                        PhoneNumberUtil.validationPhoneNumber(phoneNumber)) {

                    password = JBCriptUtil.getHashedPw(password);
                    User newUser = UserFactory.createUser(userType, username, password, phoneNumber, email);
                    Boolean isAdded = userRep.addUser(newUser);

                    if (isAdded) {
                        session.setAttribute("successMSG", "Registration successful");
                        terminus = "login.jsp";
                    } else {
                        session.setAttribute("errorMsg", "Failed to register user");
                        terminus = "register.jsp";
                    }
                } else {
                    session.setAttribute("umsg", "Registration Failed, please try again");
                    return terminus;
                }
            } catch (Exception e) {
                System.err.println("An Exception occurred while registering" + e.getMessage());
            }
        }
        return terminus;
    }
}