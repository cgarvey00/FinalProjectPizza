package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ViewLogin implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;


    public ViewLogin(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() {
        return "login.jsp";
    }


}
