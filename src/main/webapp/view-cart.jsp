<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
</head>
<body>

<%@include file="customer-nav.jsp" %>

<br><br><br><br><br><br><br><br><br><br>
<div class="box-container">
    <h2 class="heading">Shopping Cart</h2>
    <c:if test="${not empty sessionScope.orderItems}">
    <c:set var="totalCost" value="0"/>
    <c:forEach var="orderItem" items="${sessionScope.orderItems}">
        <c:set var="totalCost" value="${totalCost + (orderItem.price * orderItem.quantity)}"/>
    </c:forEach>
    <div style="display: flex; justify-content: space-between; align-items: center;">
    <h2>Total Cost: <fmt:formatNumber value="${totalCost}" maxFractionDigits="2"/> &euro;</h2>
    <form action="controller" method="post" style="text-align: right;">
        <button type="button" class="delete-btn" onclick="RemoveAll()">Remove All</button><br>
    </form>
    </div>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Cost</th>
                <th>Quantity</th>
                <th class="action-column">Action</th>
            </tr>
        </thead>
        <thead>
            <c:forEach var="orderItem" items="${sessionScope.orderItems}">
            <tr>
                <td><c:out value="${orderItem.name}"/></td>
                <td><fmt:formatNumber value="${orderItem.price}" maxFractionDigits="2"/> &euro;</td>
                <td><fmt:formatNumber value="${orderItem.cost}" maxFractionDigits="2"/> &euro;</td>
                <td>
                    <form action="controller" method="post">
                        <div class="button-container">
                            <label>
                                <input type="number" name="quantity" value="${orderItem.quantity}" data-product-id="${orderItem.productId}" style="font-size:15px;" required class="box" min="0" max="100" />
                            </label>
                            <button type="button" class="btn down-btn" onclick="decrementQuantity('${orderItem.productId}')">-</button>
                            <button type="button" class="btn up-btn" onclick="incrementQuantity('${orderItem.productId}')">+</button>
                        </div>
                    </form>
                </td>
                <td class="action-column">
                    <form action="controller" method="post">
                        <button type="button" class="delete-btn" onclick="deleteItem('${orderItem.productId}')">Delete</button>
                    </form>
                </td>
            </tr>
            </c:forEach>
        </thead>
    </table>
    <form action="controller" method="post" style="text-align: center;">
        <button type="submit" name="action" value="add-order" class="order-btn">Confirm</button>
    </form>
    </c:if>
</div>

<script>
    function incrementQuantity(productId) {
        $.ajax({
            url:'controller',
            type: 'POST',
            data:{
                ajax: true,
                action: 'increment-quantity',
                productId: productId
            },
            success: function (response) {
                if(response.success){
                    window.location.reload();
                } else {
                    alert(response.incrementMessge);
                }
            }
        })
    }

    function decrementQuantity(productId) {
        $.ajax({
            url:'controller',
            type: 'POST',
            data:{
                ajax: true,
                action: 'decrement-quantity',
                productId: productId
            },
            success: function (response) {
                if(response.success){
                    window.location.reload();
                } else {
                    alert(response.decrementMessge);
                }
            }
        })
    }

    function deleteItem(productId){
        $.ajax({
            url:'controller',
            type: 'POST',
            data:{
                ajax: true,
                action: 'delete-item',
                productId: productId
            },
            success: function (response) {
                if (response.success) {
                    window.location.reload();
                } else {
                    alert(data.deleteItemMessage);
                }
            }
        })
    }

    function RemoveAll(){
        $.ajax({
            url:'controller',
            type: 'POST',
            data:{
                ajax: true,
                action: 'clean-cart'
            },
            success: function (response) {
                if (response.success) {
                    window.location.reload();
                }
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
    table {
        table-layout: fixed;
        width: 100%;
    }

    th.action-column, td.action-column {
        width: 120px;
        max-width: 120px;
    }

    .order-btn {
        background-color: #109acb;
        color: white;
        padding: 10px 20px;
        font-size: 18px;
        border: none;
        cursor: pointer;
        min-width: 60px;
        border-radius: 5px;
    }

    .order-btn:hover {
        background-color: #017fbd;
    }

    .delete-btn {
        background-color: #ff4d4d;
        color: white;
        padding: 8px 16px;
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

    .delete-btn:hover {
        background-color: #cc0000;
    }

    input[type="number"]::-webkit-inner-spin-button,
    input[type="number"]::-webkit-outer-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }

    .button-container {
        display: flex;
        flex-direction: row;
    }

    .btn {
        background-color: #f2f2f2;
        border: 1px solid #ced4da;
        color: black;
        padding: 5px 10px;
        margin: 1px;
        cursor: pointer;
        height: 100%;
        outline: none;
    }

    .btn:hover {
        background-color: #ddd;
        transform: none;
    }

    td {
        padding: 8px;
        border-bottom: 1px solid #ddd;
    }
</style>
</html>