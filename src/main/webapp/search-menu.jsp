<%@ page import="com.finalprojectcoffee.repositories.ProductRepositories" %>
<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
</head>
<style>
    .home {
        background: -webkit-gradient(linear, left top, left bottom, from(rgba(0, 0, 0, 0.7)), to(rgba(0, 0, 0, 0.7))), url('${pageContext.request.contextPath}/images/homebg.jpg') no-repeat;
        background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('${pageContext.request.contextPath}/images/homebg.jpg') no-repeat;
        background-size: cover;
        background-position: center;
        background-attachment: fixed;
    }
</style>
<body>
<header class="header fixed-top">
    <div class="container">

        <div class="row align-items-center">

            <a href="#" class="logo mr-auto"> <i class="fa-solid fa-pizza-slice"></i> Pizza Shop </a>

            <nav class="nav">
                <a href="#">Home</a>
                <a href="controller?action=view-login">Login</a>
                <a href="controller?action=view-register">Register</a>
            </nav>

            <div class="icons">
                <div class="fas fa-bars" id="menu-btn"></div>

            </div>

        </div>

    </div>
</header>
<!-- home section starts  -->
<br><br><br><br><br><br>
<h2>View Products On Menu</h2>

<%

    List<Product> productList = (List<Product>) session.getAttribute("productList");
    if(productList != null && !productList.isEmpty()){
%>
<table>
    <%
        for(Product p : productList){
    %>
    <tr>
        <td><%=p.getId()%></td>
        <td><%=p.getName()%></td>
        <td><%=p.getCategory()%></td>
        <td><%=p.getDetails()%></td>
    </tr>
    <%
        }
    %>
</table>
<%
}else{
%>
<div>No Products currently found in the database</div>
<%
    }
%>
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