<%@ page import="com.finalprojectcoffee.entities.Order" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>
<body>
<%@include file="customer-nav.jsp" %>
<br><br><br><br><br><br><br><br><br><br>
<h1 style="text-align: center;">Order Page</h1>
<c:if test="${not empty sessionScope.orderItems}">
    <div class="show-products">
        <div class="box-container">
            <div class="box bg-light">
                <div class="content text-dark">
                    <c:forEach var="orderItem" items="${sessionScope.orderItemsInOrder}">
                    <h3 class="text-dark"><c:out value="${orderItem.getProduct().getName()}"/></h3>
                        <img src="<c:url value='/images/${orderItem.getProduct().getImage()}' />" alt="image" class="img-size">
                        <span style="font-size: 20px;"> x ${orderItem.getQuantity()} = ${orderItem.getCost()} &euro;</span>
                    </c:forEach>
                    <c:if test="${not empty sessionScope.defaultAddress}">
                    <h1 class="text-dark">Address</h1>
                    <h3>Street: <c:out value="${sessionScope.defaultAddress.getStreet()}"/></h3>
                    <h3>Eir Code: <c:out value="${sessionScope.defaultAddress.getEirCode()}"/></h3>
                    </c:if>
                    <c:if test="${not empty sessionScope.balance}">
                    <h1>Balance: <c:out value="${sessionScope.balance}"/> &euro;</h1>
                    </c:if>
                    <div class="payment-container">
                        <form name="controller" method="post">
                            <button type="submit" name="action" class="btn toPay-btn" value="pay-order">To pay</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>
<%@include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
<style>
    .img-size {
        width: 100px !important;
        height: auto !important;
    }

    .payment-container {
        display: flex;
        justify-content: center; /* Center the button horizontally */
        align-items: center; /* Center the button vertically if needed */
        margin-top: 20px; /* Add some space above the button */
    }

    .toPay-btn {
        background-color: #109acb;
        color: white;
        padding: 10px 30px; /* Increase padding to make the button bigger */
        font-size: 18px;
        border: none;
        cursor: pointer;
        border-radius: 5px;
        /* Removed width: auto; if you need a specific size, set it here */
    }

    .toPay-btn:hover {
        background-color: #017fbd;
    }
</style>
</html>