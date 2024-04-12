<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
<jsp:include page="admin-nav.jsp"/>
<br><br><br><br><br><br><br><br><br><br>
<section class="add-products">
    <h1 class="heading">Update Product</h1>
    <c:if test="${not empty sessionScope.product}">
        <form action="controller" method="post">
            <div class="flex">
                <div class="inputBox">
                    <span>Name</span>
                    <input type="text" class="box" maxlength="100"
                           placeholder="${not empty sessionScope.product ? sessionScope.product.getName() : 'Enter Name'}"
                           name="name">
                </div>
                <div class="inputBox">
                    <span>Category</span>
                    <select name="category" class="box">
                        <option value="" disabled selected>${sessionScope.product.getCategory()}</option>
                    <c:forEach var="category" items="${sessionScope.categories}">
                        <option value="${category}">${category}</option>
                    </c:forEach>
                    </select>
                </div>
                <div class="inputBox">
                    <span>Price</span>
                    <input type="text" minlength="0" class="box" maxlength="100"
                           placeholder="${not empty sessionScope.product ? sessionScope.product.getPrice() : 'Enter Price'}"
                           name="price">
                </div>
                <div class="inputBox">
                    <span>Stock</span>
                    <input type="text" minlength="0" class="box" maxlength="100"
                           placeholder="${not empty sessionScope.product ? sessionScope.product.getStock() : 'Enter Stock'}"
                           name="stock">
                </div>
                <div class="inputBox">
                    <span>Image</span>
                    <input type="text" minlength="0" class="box" maxlength="100"
                           placeholder="${not empty sessionScope.product ? sessionScope.product.getImage() : 'Enter Image'}"
                           name="image">
                </div>
                <div class="inputBox">
                    <span>Detail</span>
                    <input type="text" minlength="0" class="box" maxlength="100"
                           placeholder="${not empty sessionScope.product ? sessionScope.product.getDetails() : 'Enter Details'}"
                           name="details">
                </div>
            </div>
            <br>
            <button type="submit" name="action" value="update-product" class="update-btn">Update</button>
            <input type="hidden" name="productId" value="${sessionScope.product.getId()}">
        </form>
    </c:if>
</section>

<jsp:include page="footer.jsp"/>

</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>

<style>
    .update-btn {
        background-color: #109acb;
        color: white;
        padding: 10px 295px;
        font-size: 18px;
        border: none;
        cursor: pointer;
        min-width: 60px;
        border-radius: 5px;
        display: block;
        margin: 0 auto;
    }

    .update-btn:hover {
        background-color: #017fbd;
    }
</style>

</html>

