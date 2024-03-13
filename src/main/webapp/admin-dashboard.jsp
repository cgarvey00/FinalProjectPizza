<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page import="com.finalprojectcoffee.entities.Order" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
    @SuppressWarnings("unchecked")
    List<Product> productList = (List<Product>) request.getSession().getAttribute("productList");
    @SuppressWarnings("unchecked")
    List<User> userList = (List<User>) request.getSession().getAttribute("userList");
    @SuppressWarnings("unchecked")
    List<Order> orderList = (List<Order>) request.getSession().getAttribute("orderList");

    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || productList == null || loggedInUser == null || !"Admin".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }


%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>

<body>
<%@include file="admin-nav.jsp" %>
<section class="dashboard">
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <h1 class="heading">Admin Dashboard</h1>
    <div class="box-container">
        <!-- ADMIN  NAME AND WELCOME-->
        <div class="box">
            <h3>Welcome!</h3>
            <%
                if (loggedInUser != null) { %>
            <p><%=loggedInUser.getUsername() %>
            </p>
            <a style="font-size:13px;" class="btn"><%=loggedInUser.getEmail() %>
            </a>
            <%}%>
        </div>
        <!-- NUMBER OF TOTAL ORDERS-->
        <div class="box">
            <h3><%=orderList.size()%></h3>
            <p>orders placed</p>
            <a href="controller?action=view-orders" class="btn">see orders</a>
        </div>

        <!-- NUMBER OF PENDING ORDERS-->
        <div class="box">
            <h3><%=orderList.size()%></h3>
            <p>pending orders placed</p>
            <a href="controller?action=view-orders" class="btn">see orders</a>
        </div>

        <div class="box">
            <h3><%=productList.size()%></h3>
            <p>products added</p>
            <a href="controller?action=view-products" class="btn">see products</a>
        </div>

        <!-- NUMBER OF USERS-->
        <div class="box">
            <h3><%=userList.size()%></h3>
            <p>Users Present</p>
            <a href="controller?action=view-users" class="btn">see users</a>
        </div>
    </div>
</section>
<%@include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
</html>