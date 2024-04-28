package com.finalprojectcoffee.controllers;

import com.finalprojectcoffee.commands.Command;
import com.finalprojectcoffee.commands.CommandFactory;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "Controller", urlPatterns = {"/controller"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class Controller extends HttpServlet {
    private EntityManagerFactory factory;

    public void init() {
//        String persistenceUnit = System.getProperty("pizzashop.persistence.unit", "pizzashop");
//        System.out.println("Loading persistence unit "+persistenceUnit);
//        factory = Persistence.createEntityManagerFactory(persistenceUnit);
        String persistenceUnit = getServletContext().getInitParameter("pizzashopPersistenceUnit");

        if (persistenceUnit == null) {
            persistenceUnit = "pizzashop";
        }

        factory = Persistence.createEntityManagerFactory(persistenceUnit);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("sessionManagement".equals(action)) {
            handleSessionManagement(request, response);
        }
        processRequest(request, response);
    }

    private void clearSessionAttributes(HttpServletRequest request, String... attributes) {
        HttpSession session = request.getSession();
        for (String attr : attributes) {
            session.removeAttribute(attr);
        }
    }


    private void handleSessionManagement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String task = request.getParameter("task");
        if ("clearAttribute".equals(task)) {
            String attributeName = request.getParameter("attributeName");
            if (attributeName != null && !attributeName.isEmpty()) {
                request.getSession().removeAttribute(attributeName);
                response.getWriter().write("Attribute " + attributeName + " cleared");
            } else {
                response.getWriter().write("Attribute name missing");
            }
        }
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Command command = CommandFactory.getCommand(action, request, response, factory);
        String terminus = command.execute();
        response.sendRedirect(terminus);
    }

    public void destroy() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}
