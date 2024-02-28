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

public class DeliveryOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public DeliveryOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "order-admin";

        HttpSession session = request.getSession(true);
        Object orderIdsObj = session.getAttribute("order_ids");
        Object employeeObj = session.getAttribute("employee_ids");
        List<Integer> orderIds = new ArrayList<>();
        List<Integer> employeeIds = new ArrayList<>();


        if(orderIdsObj instanceof Integer[]){
            Integer[] orderIdsArray = (Integer[]) orderIdsObj;
            //Filter null elements
            orderIds = Arrays.stream(orderIdsArray).filter(Objects::nonNull).collect(Collectors.toList());
        }

        if(employeeObj instanceof  Integer[]){
            Integer[] employeeArray = (Integer[]) employeeObj;
            //Filter null elements
            employeeIds = Arrays.stream(employeeArray).filter(Objects::nonNull).collect(Collectors.toList());
        }

        for(int employeeId : employeeIds){
            try {
                OrderRepositories orderRep = new OrderRepositories(factory);

                //Call DeliverOrders function
                Boolean deliverOrder = orderRep.deliverOrders(orderIds, employeeId);
                if(deliverOrder){
                    session.setAttribute("ds-message", "Deliver order successfully");
                } else {
                    session.setAttribute("de-message", "Failed to deliver order");
                }
            } catch (Exception e) {
                System.err.println("An Exception occurred while delivering orders: " + e.getMessage());
            }
        }
        return terminus;
    }
}
