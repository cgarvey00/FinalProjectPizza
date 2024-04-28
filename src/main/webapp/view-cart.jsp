<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page import="com.finalprojectcoffee.entities.Carts" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

    if (request.getSession(false) == null || loggedInUser == null || !"Customer".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cart</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <link rel="icon" type="image/x-icon" href='${pageContext.request.contextPath}/uploaded-images/favicon.ico'>
    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/customer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
</head>
<body>
<style>
    .alert {
        padding: 15px;
        margin-bottom: 20px;
        border: 1px solid transparent;
        border-radius: 4px;
        color: #a94442;
        background-color: #f2dede;
        border-color: #ebccd1;
    }


    .success {
        padding: 15px;
        margin-bottom: 20px;
        border: 1px solid transparent;
        border-radius: 4px;
        color: #27942b;
        background-color: #affdb2;
        border-color: #b0ff93;
    }

</style>
<%@include file="customer-nav.jsp" %>
<br>
<br>
<br>
<section class="products" id="products">
    <%if (session.getAttribute("aps-message") != null) { %>
    <div class="success success-danger" role="alert">
        <h3>Item Removed from Cart</h3>

    </div>
    <%
            session.removeAttribute("aps-message");
        }%>
    <%if (session.getAttribute("ccs-msg") != null) { %>
    <div class="success success-danger" role="alert">
        <h3>Cart has been cleaned!</h3>

    </div>
    <%
            session.removeAttribute("ccs-msg");
        }%>

    <%if (session.getAttribute("pus-msg") != null) { %>
    <div class="success success-danger" role="alert">
        <h3>Quantity Updated!</h3>

    </div>
    <%
            session.removeAttribute("pus-msg");
        }%>
    <h3 class="heading">Shopping Cart</h3>

    <div class="box-container">
        <%
            List<Carts> cartList = (List<Carts>) request.getSession().getAttribute("cartList");
            double grandTotal = 0;
            if (!cartList.isEmpty() || cartList != null) {
                for (Carts cart : cartList) {


        %>
        <div class="box">
            <form action="controller" method='POST'>
                <input type='hidden' name='action' value='update-cart'>
                <img src="${pageContext.request.contextPath}/uploaded-images/<%=cart.getProduct().getImage()%>" name="image" alt="image">
                <div class="name" name="name">
                    <%=cart.getProduct().getName() %>
                </div>
                <div class="flex">
                    <div class="price" step="0.01" name="price"> Total : <span>&euro;
                        <%=cart.getTotalCost() %>
                           </span></div>
                    <input type='hidden' name='productId' value='<%= cart.getProduct().getId()%>'>
                    <input type="hidden" name="qty" value="<%=cart.getQuantity() %>">

                    <div><span> <input type="number" name="quantity" class="qty" min="1" max="10"
                                       onkeypress="if(this.value.length == 2) return false;"
                                       value="<%=cart.getQuantity() %>"></span></div>

                    <button type="submit" class="fas fa-edit" name="update-cart"></button>

                </div>

            </form>
            <form action="controller" method="POST">
                <input type='hidden' name='action' value='delete-cart-item'>
                <input type="hidden" name="productID" value='<%=cart.getProduct().getId() %>'>
                <input type="submit" name="delete-cart-item" class="delete-btn" value="delete"
                       onclick="return confirm('Delete This Product?');">

            </form>
        </div>
        <%
                // Total Price for Items
                grandTotal += cart.getTotalCost();
            }
        } else {
        %>
        <p class="empty">No Products added yet!</p>;
        <% }
        %>
    </div> </div>
    <div class="cart-total">
        <p>Total Cart : <span>&euro;<%=String.format("%.2f",grandTotal) %></span></p>
        <a href="controller?action=customer-view" style="text-decoration:none;" class="option-btn">continue
            shopping</a>
        <form action="controller" method='POST'>
            <input type="hidden" name="customerID" value='<%= loggedInUser.getId() %>'>
            <input type='hidden' name='action' value='clear-cart'>
            <input type="submit" name="clear-cart" style="text-decoration:none;"
                   class="delete-btn <%=(grandTotal>1) ? "" : "disabled" %>" value="Clear Cart"
                   onclick="return confirm('Delete All From Cart?');"/>
        </form>
        <form action="controller" method='POST'>
            <input type='hidden' name='action' value='view-checkout'>
            <input type="submit" name="view-checkout" style="text-decoration:none;"
                   class="btn <%=(grandTotal > 1) ? "" : "disabled" %> "  value="Proceed to Checkout"/>
        </form>
    </div>

</section>

<%@include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>

</html>