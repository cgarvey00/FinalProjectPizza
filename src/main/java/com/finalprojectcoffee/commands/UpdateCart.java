package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Carts;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.CartsRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class UpdateCart implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public UpdateCart(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-cart.jsp";

        HttpSession session = request.getSession(true);
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            User loggedInUser=(User)session.getAttribute("loggedInUser");
            CartsRepositories cartRep = new CartsRepositories(factory);

            boolean isUpdated=cartRep.updateCartItem(loggedInUser.getId(),productId,quantity);
            if(isUpdated){
                List<Carts>cartList=cartRep.getCartsByCustomerId(loggedInUser.getId());
                session.setAttribute("cartList", cartList);
                session.setAttribute("pus-msg", "Product Updated successfully");
                session.setAttribute("alertDisplayed", null);
            } else {
                session.setAttribute("pue-msg", "Failed to Update product");
            }

        } catch (Exception e) {
            System.err.println("An Exception occurred while updating product: " + e.getMessage());
        }
        return terminus;
    }
}
