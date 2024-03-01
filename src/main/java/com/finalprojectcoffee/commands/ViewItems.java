package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.CartItem;
import com.finalprojectcoffee.repositories.CartRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewItems implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewItems(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "";

        HttpSession session = request.getSession(true);
        int cartId = Integer.parseInt(request.getParameter("cartId"));

        try {
            CartRepositories cartRep = new CartRepositories(factory);

            List<CartItem> itemList = cartRep.getAllCartItemsByCartId(cartId);
            if(!itemList.isEmpty()){
                session.setAttribute("itemList", itemList);
            } else {
                session.setAttribute("emptyItemList", "Cart item list is empty");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while view cart item list: " + e.getMessage());
        }
        return terminus;
    }
}
