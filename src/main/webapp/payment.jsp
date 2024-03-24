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
<h1 style="text-align: center;">Payment</h1>
<c:if test="${not empty sessionScope.balance}">
    <c:choose>
        <c:when test="${not empty sessionScope.posMessage}">
            <div style="text-align: center;">
                <br><h1>${sessionScope.posMessage}</h1><br>
            </div>
            <div style="text-align: center;">
                <h2>You will be redirected in 3 second...</h2>
            </div>
            <script>
                setTimeout(function () {
                    window.location.href = "view-order-customer.jsp";
                }, 3000)
            </script>
        </c:when>
        <c:otherwise>
            <div class="show-products">
                <div class="box-container">
                    <div class="box bg-light">
                        <div class="content text-dark">
                            <h1 style="text-align: center;">Balance: <c:out value="${sessionScope.balance}"/>&euro;</h1>
                            <br>
                            <form action="controller" method="post" id="paymentForm">
                                <div style="text-align: center;">
                                    <label>
                                        <input type="text" name="amount" id="paymentInput" required
                                               style="width: 200px; height: 25px; font-size: 16px; border: 1px solid black;"
                                               placeholder="Enter amount">
                                    </label>
                                </div>
                                <br>
                                <div style="text-align: center;">
                                    <button type="submit" name="action" value="pay-order" class="pay-btn">Pay</button>
                                    <input type="hidden" name="orderId" value="${sessionScope.orderId}">
                                </div>
                            </form>
                            <script>
                                document.getElementById('paymentForm').onsubmit = function (event) {
                                    var paymentInput = document.getElementById('paymentInput').value;

                                    if(isNaN(paymentInput) || paymentInput === ''){
                                        alert('Please enter a valid number.')
                                        event.preventDefault();
                                    } else if( paymentInput < 0){
                                        alert('Please enter a valid number.')
                                        event.preventDefault();
                                    } else if( paymentInput < sessionStorage.balance){
                                        alert('Payment amount is insufficient.')
                                        event.preventDefault();
                                    }
                                }
                            </script>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>

<%@include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>

<style>
    .pay-btn {
        background-color: #109acb;
        color: white;
        padding: 5px 20px;
        font-size: 18px;
        border: none;
        cursor: pointer;
        min-width: 60px;
        border-radius: 5px;
    }

    .pay-btn:hover {
        background-color: #017fbd;
    }
</style>
</html>
