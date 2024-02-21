<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
<header class="header fixed-top">
    <div class="container">
        <div class="row align-items-center">
            <a href="#" class="logo mr-auto"> <i class="fa-solid fa-pizza-slice"></i> Pizza Shop </a>
            <nav class="nav">
                <a href="controller?action=home">Dashboard</a>
                <a href="controller?action=admin-product">View Products</a>
                <a href="controller?action=view-register">Logout</a>
            </nav>
            <div class="icons">
                <div class="fas fa-bars" id="menu-btn"></div>
            </div>
        </div>
    </div>
</header>
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
    <% User loggedInUser=(User) session.getAttribute("loggedInUser"); %>
    <div class="box-container">
        <!-- ADMIN  NAME AND WELCOME-->
        <div class="box">
            <h3>Welcome!</h3>
          <p><%loggedInUser.getUsername(); %></p>
            <a style="font-size:13px;"class="btn"><%loggedInUser.getEmail(); %></a>
        </div>


        <!-- NUMBER OF ORDERS-->
        <div class="box">
            <p>0 orders placed</p>
            <a href="" class="btn">see orders</a>
        </div>

        <div class="box">
            <%
                List<Product> productList = (List<Product>) request.getSession().getAttribute("productList");%>
          <h3><%=productList.size()%></h3>
            <p>products added</p>
            <a href="?action=view-stock" class="btn">see products</a>
        </div>
    </div>
        </section>
        <section class="footer container">

            <a href="#" class="logo"> <i class="fa-solid fa-pizza-slice"></i> Pizza Shop </a>

            <p class="credit"> created by <span>Conor,Tom and Matthew</span> | all rights reserved! © 2024 </p>

            <div class="share">
                <a href="#" class="fab fa-facebook-f"></a>
                <a href="#" class="fab fa-linkedin"></a>
                <a href="#" class="fab fa-twitter"></a>
                <a href="#" class="fab fa-github"></a>
            </div>

        </section>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        <script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
</html>