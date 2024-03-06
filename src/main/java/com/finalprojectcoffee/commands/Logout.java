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

public class Logout implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public Logout(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        HttpSession session = request.getSession();
        session.removeAttribute("loggedInUser");
        session.invalidate();
        return "index.jsp";
    }
}