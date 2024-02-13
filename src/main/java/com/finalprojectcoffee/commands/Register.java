package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import com.finalprojectcoffee.utils.EmailUtil;
import com.finalprojectcoffee.utils.JBCriptUtil;
import com.finalprojectcoffee.utils.PhoneNumberUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
            EntityManager entityManager = factory.createEntityManager();

            try {
                UserRepositories userRep = new UserRepositories(factory);
                User user = userRep.findUserByUsername(username);

                if(user == null){
                    if(JBCriptUtil.validatePassword(password)){
                        if(password.equals(passwordConfirmation)){
                            if(EmailUtil.isValid(email)){
                                if(PhoneNumberUtil.validationPhoneNumber(phoneNumber)){
                                    if("customers".equals(userType)){
                                        Customer newCustomer = new Customer();
                                        newCustomer.setUsername(username);
                                        newCustomer.setPassword(JBCriptUtil.getHashedPw(password));
                                        newCustomer.setEmail(email);
                                        newCustomer.setPhoneNumber(phoneNumber);
                                        if(userRep.addUser(newCustomer)){
                                            terminus = "login.jsp";
                                        }
                                    } else if("employees".equals(userType)){
                                        Employee newEmployee = new Employee();
                                        newEmployee.setUsername(username);
                                        newEmployee.setPassword(JBCriptUtil.getHashedPw(password));
                                        newEmployee.setEmail(email);
                                        newEmployee.setPhoneNumber(phoneNumber);
                                        if(userRep.addUser(newEmployee)){
                                            terminus = "login.jsp";
                                        }
                                    } else {
                                        User newUser = new User();
                                        newUser.setUsername(username);
                                        newUser.setPassword(JBCriptUtil.getHashedPw(password));
                                        newUser.setEmail(email);
                                        newUser.setPhoneNumber(phoneNumber);
                                        if(userRep.addUser(newUser)){
                                            terminus = "login.jsp";
                                        }
                                    }
                                } else {
                                    session.setAttribute("pnmsg", "Phone number format error");
                                }
                            } else {
                                session.setAttribute("emsg", "Email format error");
                            }
                        } else {
                            session.setAttribute("pwcmsg", "Password inconsistency");
                        }
                    } else {
                        session.setAttribute("pwvmsg","Password format error");
                    }
                } else {
                    session.setAttribute("umsg","User exist");
                }
            } finally {
                entityManager.close();
            }
        }
        return terminus;
    }
}
