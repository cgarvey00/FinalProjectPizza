package com.finalprojectcoffee.controllers;

import com.finalprojectcoffee.commands.Command;
import com.finalprojectcoffee.commands.CommandFactory;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Controller",urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private EntityManagerFactory factory;
    public void init(){factory = Persistence.createEntityManagerFactory("testpizzashop");}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action =request.getParameter("action");
        Command command = CommandFactory.getCommand(action,request,response,factory);
        String terminus = command.execute();
        response.sendRedirect(terminus);
    }

    public void destroy(){
        if(factory != null && factory.isOpen()){
            factory.close();
        }
    }
}
