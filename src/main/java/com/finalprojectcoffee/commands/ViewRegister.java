package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.commands.Command;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewRegister implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;


    public ViewRegister(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String execute() {
        return "register.jsp";
    }


}
