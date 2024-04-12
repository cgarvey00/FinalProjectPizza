package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.ProductCategory;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ToAddProduct implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ToAddProduct(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "add-product.jsp";
        HttpSession session = request.getSession(true);
        ProductCategory[] categories = ProductCategory.values();
        session.setAttribute("categories", categories);
        return terminus;
    }
}
