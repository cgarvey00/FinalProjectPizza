package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.dto.OrderItemDTO;
import com.finalprojectcoffee.entities.*;
import com.finalprojectcoffee.repositories.OrderRepositories;
import com.finalprojectcoffee.repositories.ProductRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();
        @SuppressWarnings("unchecked")
        List<OrderItemDTO> orderItemsDTO = (List<OrderItemDTO>) session.getAttribute("orderItems");

        try {
            UserRepositories userRep = new UserRepositories(factory);
            OrderRepositories orderRep = new OrderRepositories(factory);
            ProductRepositories productRep = new ProductRepositories(factory);

            //Get default address
            Address defaultAddress = userRep.getDefaultAddress(activeUserId);
            int addressId = defaultAddress.getId();

            Order order = orderRep.addOrder(activeUserId, addressId);
            List<OrderItem> orderItemsInOrder = new ArrayList<>();

            if(orderItemsDTO != null && !orderItemsDTO.isEmpty()){

                for(OrderItemDTO orderItemDTO : orderItemsDTO){
                    OrderItem orderItem = new OrderItem();
                    Product product = productRep.findProductByID(orderItemDTO.getProductId());
                    orderItem.setProduct(product);
                    orderItem.setOrder(order);
                    orderItem.setQuantity(orderItemDTO.getQuantity());
                    orderItem.setCost(orderItemDTO.getCost());
                    orderItemsInOrder.add(orderItem);
                }
            }
            //Add order items
            Boolean isAdded = orderRep.addOrderItem(orderItemsInOrder);
            if(!orderItemsInOrder.isEmpty() && isAdded){
                order = orderRep.findOrderById(order.getId());
                session.setAttribute("orderItemsInOrder", orderItemsInOrder);
                session.setAttribute("addressInorder", defaultAddress);
                session.setAttribute("order", order);
                session.setAttribute("orderId", order.getId());
                session.setAttribute("userType", "customer");
                session.removeAttribute("orderItems");
                terminus = "order-page.jsp";
            } else {
                session.setAttribute("errorMessage", "Failed to add order. Please try again later.");
                terminus = "error.jsp";
            }
        } catch (NoResultException e){
            session.setAttribute("errorMessage", "Please add an <a href='add-address.jsp'>address</a> first.");
            return "error.jsp";
        } catch (Exception e) {
            System.err.println("An Exception occurred while adding order: " + e.getMessage());
            session.setAttribute("errorMessage", "Something went wrong");
            return "error.jsp";
        }

        return terminus;
    }
}
