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
<c:choose>
    <c:when test="${sessionScope.userType == 'customer'}">
        <jsp:include page="customer-nav.jsp" />
    </c:when>
    <c:when test="${sessionScope.userType == 'admin'}">
        <jsp:include page="admin-nav.jsp" />
    </c:when>
</c:choose>
<br><br><br><br><br><br><br><br><br><br>
<h1 style="text-align: center;">Order Page</h1>
<c:if test="${not empty sessionScope.orderItemsInOrder}">
    <div class="show-products">
        <div class="box-container">
            <div class="box bg-light">
                <div class="content text-dark">
                    <c:forEach var="orderItem" items="${sessionScope.orderItemsInOrder}">
                        <h3 class="text-dark"><c:out value="${orderItem.getProduct().getName()}"/></h3>
                        <img src="<c:url value='/images/${orderItem.getProduct().getImage()}' />" alt="image"
                             class="img-size">
                        <span style="font-size: 20px;"> x ${orderItem.getQuantity()} = ${orderItem.getCost()} &euro;</span>
                    </c:forEach>
                    <c:if test="${not empty sessionScope.addressInOrder}">
                        <h1 class="text-dark">Address</h1>
                        <h3>Street: <c:out value="${sessionScope.addressInOrder.getStreet()}"/></h3>
                        <h3>Eir Code: <c:out value="${sessionScope.addressInOrder.getEirCode()}"/></h3>
                    </c:if>
                    <c:if test="${not empty sessionScope.order}">
                        <h1>Balance: <c:out value="${sessionScope.order.getBalance()}"/> &euro;</h1>
                    </c:if>
                    <c:choose>
                        <c:when test="${sessionScope.order.pending && sessionScope.userType == 'customer'}">
                            <form action="controller" method="post">
                                <div class="button-container">
                                    <button type="submit" name="action" value="cancel-order" class="btn cancel-btn">
                                        Cancel
                                    </button>
                                    <button type="submit" name="action" value="to-payment" class="btn toPay-btn">To
                                        Pay
                                    </button>
                                    <input type="hidden" name="orderId" value="${sessionScope.order.getId()}">
                                    <input type="hidden" name="userType" value="customer">
                                </div>
                            </form>
                        </c:when>
                        <c:when test="${sessionScope.order.pending && sessionScope.userType == 'admin'}">
                            <form action="controller" method="post">
                                <div class="button-container">
                                    <button type="submit" name="action" value="cancel-order" class="btn cancel-btn">Cancel</button>
                                    <button type="submit" name="action" value="finish-order" class="btn finish-btn">Finish</button>
                                    <input type="hidden" name="orderId" value="${sessionScope.order.getId()}">
                                    <input type="hidden" name="userType" value="admin">
                                </div>
                            </form>
                        </c:when>
                    </c:choose>
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

    .button-container {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 20px;
        margin-bottom: 20px;
    }

    .btn {
        color: white;
        padding: 10px 20px;
        font-size: 18px;
        border: none;
        cursor: pointer;
        border-radius: 5px;
        box-sizing: border-box;
        width: 44%;
        margin-inside: 6%;
    }

    .cancel-btn{
        background-color: #ff4d4d;
    }

    .cancel-btn:hover{
        background-color: #cc0000;
    }

    .toPay-btn, .finish-btn {
        background-color: #109acb;
    }

    .toPay-btn:hover, .finish-btn:hover {
        background-color: #017fbd;
    }
</style>
</html>