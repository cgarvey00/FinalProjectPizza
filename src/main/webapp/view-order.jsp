<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>
<body>
<%@include file="customer-nav.jsp" %>
<br><br><br><br><br><br><br><br><br><br>
<div class="box-container">
    <h1 style="text-align: center;">Orders</h1>
    <c:if test="${not empty sessionScope.ordersCustomer}">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Create Time</th>
            <th>Status</th>
            <th>Payment Status</th>
            <th>Balance</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${sessionScope.ordersCustomer}">
        <tr>
            <td><c:out value="${order.get()}"/></td>
            <td><c:out value="${order.getCreateTime()}"/></td>
            <td><c:out value="${order.getStatus()}"/></td>
            <td><c:out value="${order.getPaymentStatus}"/></td>
            <td><c:out value="${order.getBalance()}"/></td>
            <td>
                <form action="controller" method="post">
                    <button type="button" name="action" value="update-order" class="update-btn">Update</button>
                    <button type="button" name="action" onclick="cancelOrder('${order.getId()}')" class="cancel-btn">Cancel</button>
                </form>
            </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:if>
</div>

<script>
    function cancelOrder(orderId){
        $.ajax({
            url: 'controller',
            type: 'POST',
            data:{
                ajax: true,
                action: 'update-order',
                orderId: orderId
            }
        })
    }

</script>

<%@include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
<style>
    .update-btn {
        background-color: #109acb;
        color: white;
        padding: 5px 10px;
        font-size: 18px;
        border: none;
        cursor: pointer;
        min-width: 60px;
        border-radius: 5px;
    }

    .cancel-btn {
        background-color: #ff4d4d;
        color: white;
        padding: 10px 20px;
        font-size: 16px;
        border: none;
        cursor: pointer;
        border-radius: 5px;
        transition: background-color 0.3s;
        display: block;
        width: 100%;
        box-sizing:border-box;
        margin:0;
    }
</style>
</html>
