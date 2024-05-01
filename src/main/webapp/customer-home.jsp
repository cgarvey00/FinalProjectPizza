<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || loggedInUser == null || !"Customer".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

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

    .home {
        background: -webkit-gradient(linear, left top, left bottom, from(rgba(0, 0, 0, 0.7)), to(rgba(0, 0, 0, 0.7))), url('${pageContext.request.contextPath}/images/homebg.jpg') no-repeat;
        background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('${pageContext.request.contextPath}/images/homebg.jpg') no-repeat;
        background-size: cover;
        background-position: center;
        background-attachment: fixed;
    }
</style>
<body>
<jsp:include page="customer-nav.jsp"/>

<c:if test="${!sessionScope.addressed}">
    <div class="message">
        <span>Please Add an Address!<a href="add-address.jsp">Address Page</a></span>
        <i class="fas fa-times" onclick="this.parentElement.remove();"></i>
    </div>
</c:if>

<!-- home section starts  -->

<section class="home" id="home">
    <div class="container">
        <div class="row align-items-center text-center text-md-left min-vh-100">
            <div class="col-md-6">
                <span>Welcome to the Pizza Shop</span>
                <h3>Browse Our Menu </h3>
                <a href="#" class="link-btn">Get Started</a>
            </div>
        </div>
    </div>
</section>

<section class="product-recommendation">
    <div class="container py-5">
        <h2 class="text-center mb-4">Recommended for You</h2>
        <div class="row">
            <c:forEach var="product" items="${sessionScope.recommendedProducts}" varStatus="status">
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <a href="view-menu.jsp#product-${product.id}">
                            <img class="card-img-top" src="${pageContext.request.contextPath}/images/${product.getImage()}" alt="Product Image">
                        </a>
                        <div class="card-body">
                            <h5 class="card-title">${product.getName()}</h5>
                            <p class="card-text">${product.getDetails()}</p>
                        </div>
                    </div>
                </div>
                <c:if test="${status.index % 3 == 2}">
                    <div class="w-100"></div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>

<style>
    .product-recommendation .card {
        box-shadow: var(--box-shadow);
        transition: transform .3s ease-in-out;
        min-height: 300px;
    }

    .product-recommendation .card:hover {
        transform: translateY(-10px);
    }

    .product-recommendation h2 {
        color: var(--main-color);
    }

    .product-recommendation .card-title {
        font-size: 2em;
    }

    .product-recommendation .card-text {
        font-size: 1.5em;
    }

    .product-recommendation .card-img-top {
        width: 100%;
        height: 200px;
        object-fit: cover;
    }

    .product-recommendation h2 {
        font-size: 28px;
        font-weight: bold;
        color: #5A3422;
        padding: 10px 0;
        margin: 20px 0;
        text-align: center;
        border-bottom: 3px dashed #FFA500;
        text-transform: uppercase;
        font-family: 'Brush Script MT', cursive;
        letter-spacing: 1px;
        word-spacing: 2px;
        text-shadow: 2px 2px 3px #aaa;
    }
</style>
</html>