package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import com.finalprojectcoffee.utils.EmailUtil;
import com.finalprojectcoffee.utils.JBCryptUtil;
import com.finalprojectcoffee.utils.PhoneNumberUtil;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class Register implements Command{
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
        String userType = request.getParameter("userType");

        if(username != null && !username.isEmpty() && password != null && !password.isEmpty() && passwordConfirmation != null && !passwordConfirmation.isEmpty() && email != null && !email.isEmpty() && phoneNumber != null && !phoneNumber.isEmpty()){
            try {
                UserRepositories userRep = new UserRepositories(factory);
                User user = userRep.findUserByUsername(username);

                if(user != null){
                    session.setAttribute("umsg", "User already exists");
                    if(userType.equals("Customer")){
                        return "register.jsp";
                    }
                    if(userType.equals("Employee")){
                        return "employee-register.jsp";
                    }
                }

                Boolean isExistedPhoneNumber = userRep.findExistingPhoneNumber(phoneNumber);
                if(isExistedPhoneNumber){
                    session.setAttribute("pnmsg", "Phone number already exists");
                    if(userType.equals("Customer")){
                        return "register.jsp";
                    }
                    if(userType.equals("Employee")){
                        return "employee-register.jsp";
                    }
                }

                Boolean isExistedEmail = userRep.findExistingEmail(email);
                if (isExistedEmail) {
                    session.setAttribute("emsg", "Email already exists");
                    if(userType.equals("Customer")){
                        return "register.jsp";
                    }
                    if(userType.equals("Employee")){
                        return "employee-register.jsp";
                    }
                }

                if(!EmailUtil.validateEmail(email)){
                    session.setAttribute("emsg", "Email format error");
                    if(userType.equals("Customer")){
                        return "register.jsp";
                    }
                    if(userType.equals("Employee")){
                        return "employee-register.jsp";
                    }
                }

                if(!PhoneNumberUtil.validationPhoneNumber(phoneNumber)){
                    session.setAttribute("pnmsg", "Phone number format error");
                    if(userType.equals("Customer")){
                        return "register.jsp";
                    }
                    if(userType.equals("Employee")){
                        return "employee-register.jsp";
                    }
                }

                if(!JBCryptUtil.validatePassword(password)){
                    session.setAttribute("pwvmsg", "Password format error");
                    if(userType.equals("Customer")){
                        return "register.jsp";
                    }
                    if(userType.equals("Employee")){
                        return "employee-register.jsp";
                    }
                }

                if(!password.equals(passwordConfirmation)){
                    session.setAttribute("pwcmsg", "Password inconsistency");
                    if(userType.equals("Customer")){
                        return "register.jsp";
                    }
                    if(userType.equals("Employee")){
                        return "employee-register.jsp";
                    }
                }

                password = JBCryptUtil.getHashedPw(password);
                User newUser = UserFactory.createUser(userType, username, password, phoneNumber, email);
                Boolean isAdded = userRep.addUser(newUser);
                if(isAdded){
                    session.setAttribute("successmsg", "Registration successful");
                    if(userType.equals("Customer")){
                        return "login.jsp";
                    }
                    if(userType.equals("Employee")){
                        List<Employee> employeeList = userRep.getAllEmployees();
                        session.setAttribute("employeeList", employeeList);
                        return "view-all-employees.jsp";
                    }
                } else {
                    session.setAttribute("errormsg", "Failed to register");
                }
            } catch (Exception e){
                System.err.println("An Exception occurred while registering" + e.getMessage());
            }
        }
        return terminus;
    }
}