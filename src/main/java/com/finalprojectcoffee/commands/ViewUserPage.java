package com.finalprojectcoffee.commands;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewUserPage implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;


    public ViewUserPage(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() {
        return "customer-home.jsp";
    }


}
