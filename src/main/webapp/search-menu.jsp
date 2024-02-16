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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
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
                <a href="controller?action=home">Home</a>
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
<br><br><br><br><br><br><br><br><br><br><br><br>
<div class="show-products" id="products">
    <h1 style="text-align: center;">View Products On Menu</h1>
    <div class="box-container">
        <%
            List<Product> productList = (List<Product>) request.getSession().getAttribute("productList");
            if (productList != null && !productList.isEmpty()) {
                for (Product p : productList) { %>
        <form action="." method="post">
            <input type="hidden" name="action" value="add-to-cart">
            <input type="hidden" name="product_id" value=<%=p.getId()%>>
            <input type="hidden" name="name" value=<%=p.getName()%>>
            <input type="hidden" name="price" value=<%=p.getPrice()%>>
            <div class="box bg-light">
                <div class="image">
                    <img src="${pageContext.request.contextPath}/uploaded-images/<%= p.getImage() %>" name="image" alt="image">
                </div>
                <div class="content text-dark">
                    <h3 class="text-dark" name="name">
                        <%=p.getName()  %>
                    </h3>
                    <h3 class="text-dark" style="font-size:10px;">
                        <%=p.getDetails()  %>
                    </h3>
                    <div class="price">
                        <p class="text-dark" step="0.01">&euro;
                            <%=String.format("%.2f", p.getPrice())  %>
                        </p>
                    </div>
                    <h4 class="text-dark">Place cart quantity</h4>
                    <label>
                        <input type="number" name="qty" style="font-size:15px;" required class="box" min="0" max="40" placeholder="enter quantity" value="1">
                    </label>
                    <button type="submit" name="add-to-cart" class="cart-btn">Add to Cart</button>

                </div>
            </div>
        </form>
        <% } %>
    </div>
    <% } else { %>
    <p class="empty">No Products Added Yet!</p>
    <% } %>
</div>

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