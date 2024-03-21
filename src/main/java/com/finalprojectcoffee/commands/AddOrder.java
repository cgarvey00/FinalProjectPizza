package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.dto.OrderItemDTO;
import com.finalprojectcoffee.entities.*;
import com.finalprojectcoffee.repositories.OrderRepositories;
import com.finalprojectcoffee.repositories.ProductRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public AddOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus;
        HttpSession session = request.getSession(true);
        User activeCustomer = (User) session.getAttribute("loggedInUser");
        int activeCustomerId = activeCustomer.getId();
        @SuppressWarnings("unchecked")
        List<OrderItemDTO> orderItemsDTO = (List<OrderItemDTO>) session.getAttribute("orderItems");

        try {
            UserRepositories userRep = new UserRepositories(factory);
            OrderRepositories orderRep = new OrderRepositories(factory);
            ProductRepositories productRep = new ProductRepositories(factory);

            //Get default address
            Address defaultAddress = userRep.getDefaultAddress(activeCustomerId);
            int addressId = defaultAddress.getIsDefault();

            Order order = orderRep.addOrder(activeCustomerId, addressId);
            List<OrderItem> orderItemsInOrder = new ArrayList<>();
            double balance = 0.0;

            if(orderItemsDTO != null && !orderItemsDTO.isEmpty()){

                for(OrderItemDTO orderItemDTO : orderItemsDTO){
                    OrderItem orderItem = new OrderItem();
                    Product product = productRep.findProductByID(orderItemDTO.getProductId());
                    orderItem.setProduct(product);
                    orderItem.setOrder(order);
                    orderItem.setQuantity(orderItemDTO.getQuantity());
                    orderItem.setCost(orderItemDTO.getCost());
                    balance += orderItem.getCost();
                    orderItemsInOrder.add(orderItem);
                }
            }
            //Add order items
            Boolean isAdded = orderRep.addOrderItem(orderItemsInOrder);
            if(!orderItemsInOrder.isEmpty() && isAdded){
                session.setAttribute("orderItemsInOrder", orderItemsInOrder);
                session.setAttribute("defaultAddress", defaultAddress);
                session.setAttribute("balance", balance);
                terminus = "order-page.jsp";
            } else {
                session.setAttribute("errorMessage", "Failed to add to order");
                return "error.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while adding order: " + e.getMessage());
            session.setAttribute("errorMessage", "Something went wrong");
            return "error.jsp";
        }

        return terminus;
    }
}
