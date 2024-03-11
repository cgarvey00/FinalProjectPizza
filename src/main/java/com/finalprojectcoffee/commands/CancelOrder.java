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

public class CancelOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public CancelOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "order-page";

        HttpSession session = request.getSession(true);
        Object orderIdsObj = session.getAttribute("cancel_order_ids");
        List<Integer> orderIds = new ArrayList<>();

        if(orderIdsObj instanceof Integer[]){
            Integer[] orderIdsArray = (Integer[]) orderIdsObj;
            //Filter null elements
            orderIds = Arrays.stream(orderIdsArray).filter(Objects::nonNull).collect(Collectors.toList());
        }

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            Boolean isCancelled = orderRep.cancelOrders(orderIds);
            if (isCancelled) {
                session.setAttribute("cos-msg", "Cancel orders successfully");
            } else {
                session.setAttribute("coe-msg", "Failed to cancel orders");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while cancelling orders: " + e.getMessage());
        }
        return terminus;
    }
}
