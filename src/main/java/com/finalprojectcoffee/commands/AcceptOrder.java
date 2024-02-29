package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AcceptOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public AcceptOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "order-admin";

        HttpSession session = request.getSession(true);
        Object orderIdsObj = session.getAttribute("order_ids");
        List<Integer> orderIds = new ArrayList<>();

        if(orderIdsObj instanceof Integer[]){
            Integer[] orderIdsArray = (Integer[]) orderIdsObj;
            //Filter null elements
            orderIds = Arrays.stream(orderIdsArray).filter(Objects::nonNull).collect(Collectors.toList());
        }

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            if(!orderIds.isEmpty()){
                orderRep.acceptOrders(orderIds);
                session.setAttribute("aos_message", "Accept order successfully");
            } else {
                session.setAttribute("aoe_message", "Failed to accept order");
            }

        } catch (Exception e) {
            System.err.println("An Exception occurred while accepting order: " + e.getMessage());
        }
        return terminus;
    }
}
