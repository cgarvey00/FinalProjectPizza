<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.finalprojectcoffee.repositories.UserRepositories" %>
<%@ page import="com.finalprojectcoffee.entities.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || loggedInUser == null || !"Employee".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }

    request.setAttribute("loggedInUser", loggedInUser);

%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Page</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <link rel="icon" type="image/x-icon" href='${pageContext.request.contextPath}/uploaded-images/favicon.ico'>
    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/customer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">

</head>
<style>
    :root {
        --main-color: #2980b9;
        --orange: #f39c12;
        --red: #e74c3c;
        --black: #333;
        --white: #fff;
        --light-color: #666;
        --light-bg: #eee;
        --border: .2rem solid var(--black);
        --box-shadow: 0 .5rem 1rem rgba(0, 0, 0, .1);
    }
</style>
<body>
<%@include file="customer-nav.jsp" %>
<section class="products" style="padding-top: 0; min-height:100vh;">
    <br><br><br><br><br><br>
    <div class="box-container">
        <%
            List<OrdersItem> orderDetailList = (List<com.finalprojectcoffee.entities.OrdersItem>) request.getSession().getAttribute("orderDetailList");
            double orderTotal = 0;
            if (orderDetailList != null && !orderDetailList.isEmpty()) {
                for (OrdersItem orderDet : orderDetailList) { %>
        <div class="box bg-light">
            <div class="image">
                <img src="${pageContext.request.contextPath}/uploaded-images/<%= orderDet.getProduct().getImage() %>"
                     name="image" alt="image">
            </div>
            <div class="content text-dark">
                <h3 class="text-dark" name="name">
                    <%=orderDet.getProduct().getName()  %>
                </h3>
                <h3 class="text-dark" style="font-size:10px;">
                    <%=orderDet.getProduct().getDetails()  %>
                </h3>
                <div class="price">
                    <p class="text-dark" step="0.01">&euro;
                        <%=String.format("%.2f", orderDet.getCost())  %>
                    </p>
                </div>
                <h4 class="text-dark">Quantity</h4>
                <div class="content text-dark">
                    <h3 class="text-dark" name="name">
                        <%=orderDet.getQuantity()  %>
                    </h3>

                </div>
            </div>
        </div>
        <%
                orderTotal += orderDet.getCost();
            } %>
    </div>
    <% } else { %>
    <p class="empty">No Order Items Available </p>
    <% } %>
    </div>

    <div class="cart-total">
        <p>Total Cart : <span>&euro;<%=String.format("%.2f", orderTotal) %></span></p>
        <a href="controller?action=customer-view" style="text-decoration:none;" class="option-btn">continue
            shopping</a>
        <form action="controller" method='POST'>
            <input type="hidden" name="customerID" value='<%= loggedInUser.getId() %>'>
            <input type='hidden' name='action' value='clear-cart'>

        </form>
        <form action="controller" method='POST'>
            <input type='hidden' name='action' value='view-checkout'>
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
