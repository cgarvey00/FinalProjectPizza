package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.ProductRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import com.finalprojectcoffee.utils.JBCriptUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class UserPage implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;
    private final String page;

    public UserPage(String page, HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.page = page;
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {

        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("userRole");

        switch (userRole) {
            case "Admin":
                setAdminAttribute(session);
                return "admin-dashboard.jsp";
            case "Customer":
                setCustomerAttribute(session);
                return "customer-home.jsp";
            case "Employee":
                return "employee-page.jsp";
            default:
                return "redirect:login.jsp";


        }

    }

    private void setAdminAttribute(HttpSession session){
        EntityManager eManager= factory.createEntityManager();
        try{
            User loggedInUser=(User) session.getAttribute("loggedInUser");
            String adminUsername=loggedInUser.getUsername();

            session.setAttribute("loggedInUser",loggedInUser);

            session.setAttribute("adminUsername",adminUsername);

            ProductRepositories productRepos= new ProductRepositories(factory);

            List<Product>productList=productRepos.getAllProducts();

            session.setAttribute("productList",productList);

        }finally {
            eManager.close();
        }
    }

    private void setCustomerAttribute(HttpSession session){
        EntityManager eManager= factory.createEntityManager();
        try{
            User loggedInUser=(User) session.getAttribute("loggedInUser");
            String adminUsername=loggedInUser.getUsername();

            session.setAttribute("customerUsername",adminUsername);

            ProductRepositories productRepos= new ProductRepositories(factory);

            List<Product>productList=productRepos.getAllProducts();

            session.setAttribute("productList",productList);

        }finally {
            eManager.close();
        }
    }

    private void setEmployeeAttribute(HttpSession session){
        EntityManager eManager= factory.createEntityManager();
        try{
            User loggedInUser=(User) session.getAttribute("loggedInUser");
            String adminUsername=loggedInUser.getUsername();

            session.setAttribute("employeeUsername",adminUsername);

            ProductRepositories productRepos= new ProductRepositories(factory);

            List<Product>productList=productRepos.getAllProducts();

            session.setAttribute("productList",productList);

        }finally {
            eManager.close();
        }
    }
}