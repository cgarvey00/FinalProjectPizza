package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Carts;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.CartsRepositories;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class DeleteCart implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public DeleteCart(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-cart.jsp";
        HttpSession session = request.getSession(true);

        int productID = Integer.parseInt(request.getParameter("productID"));
//        int customerID = Integer.parseInt(request.getParameter("customerID"));
        try {
            ProductRepositories productRep = new ProductRepositories(factory);
            CartsRepositories cartRep = new CartsRepositories(factory);

            User loggedInUser=(User)session.getAttribute("loggedInUser");

            if (loggedInUser!=null) {
                boolean isAdded = cartRep.deleteCartItem(loggedInUser.getId(),productID);
                if (isAdded) {
                    // Updating the Cart Items
                    List<Carts> cartList = cartRep.getCartsByCustomerId(loggedInUser.getId());
                    session.setAttribute("cartLists",cartList.size());
                    session.setAttribute("cartList", cartList);
                    session.setAttribute("aps-message", "Item Deleted from Cart successfully");
                } else {
                    session.setAttribute("ape-message", "Failed to Delete Cart");
                }
            }else{
                session.setAttribute("log-message","User logged out");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while adding products: " + e.getMessage());
        }
        return terminus;
    }
}