<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="http://localhost:8080/FinalProjectCoffee_war_exploded/tags" %>
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
<jsp:include page="customer-nav.jsp"/>
<br><br><br><br><br><br><br><br><br><br>
<div class="box-container">
    <h1 style="text-align: center;">Orders</h1>
    <c:if test="${not empty sessionScope.orderListCustomer}">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Create Time</th>
                <th>Status</th>
                <th>Payment Status</th>
                <th>Balance</th>
                <th>Address</th>
                <th class="action-column">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${sessionScope.orderListCustomer}" varStatus="status">
                <tr>
                    <td><c:out value="${order.getId()}"/></td>
                    <td>${my:formatLocalDateTime(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss")}</td>
                    <td><c:out value="${order.getStatus()}"/></td>
                    <td><c:out value="${order.getPaymentStatus()}"/></td>
                    <td><c:out value="${order.getBalance()}"/>&euro;</td>
                    <td>
                        <c:choose>
                            <c:when test="${order.pending}">
                                <form action="controller" method="post">
                                    <label>
                                        <select id="addressSelect${status.index}" name="selectedAddressId"
                                                data-initial-id="${order.getAddress().getId()}"
                                                onchange="checkAddressId(${status.index})">
                                            <option value="${order.getAddress().getId()}" selected><c:out
                                                    value="${order.getAddress().getEirCode()}"/></option>
                                            <c:if test="${not empty sessionScope.addressList}">
                                                <c:forEach var="address" items="${sessionScope.addressList}">
                                                    <c:if test="${address.getEirCode() ne order.getAddress().getEirCode()}">
                                                        <option value="${address.getId()}"><c:out
                                                                value="${address.getEirCode()}"/></option>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                    </label>
                                    <button type="submit" name="action" value="update-address-in-order"
                                            id="updateBtn${status.index}" class="update-address" style="display: none">
                                        Update
                                    </button>
                                    <input type="hidden" name="orderId" value="${order.getId()}">
                                </form>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${order.getAddress().getEirCode()}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form action="controller" method="post">
                            <button type="submit" name="action" value="view-order" class="detail-btn">Detail</button>
                            <input type="hidden" name="orderId" value="${order.getId()}"/>
                            <input type="hidden" name="userType" value="customer">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<jsp:include page="footer.jsp"/>

<script>
    function checkAddressId(index) {
        var initialAddressId = document.getElementById('addressSelect' + index).getAttribute('data-initial-id');
        var selectedAddressId = document.getElementById('addressSelect' + index).value;
        var updateBtn = document.getElementById('updateBtn' + index);

        if (initialAddressId !== selectedAddressId) {
            updateBtn.style.display = 'inline';
        } else {
            updateBtn.style.display = 'none';
        }
    }
</script>

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

    .detail-btn {
        background-color: #109acb;
        color: white;
        padding: 5px 10px;
        font-size: 16px;
        border: none;
        cursor: pointer;
        border-radius: 5px;
        transition: background-color 0.3s;
        display: block;
        width: 100%;
        box-sizing: border-box;
        margin: 0;
    }

    .detail-btn:hover {
        background-color: #017fbd;
    }

    .cancelled-btn {
        background-color: #ff4d4d;
        color: white;
        padding: 5px 10px;
        font-size: 16px;
        border: none;
        cursor: not-allowed;
        border-radius: 5px;
        transition: background-color 0.3s;
        display: block;
        width: 100%;
        box-sizing: border-box;
        margin: 0;
    }

    .update-address {
        background-color: #4CAF50;
        color: white;
        padding: 3px 6px;
        margin-left: 10px;
        border-radius: 3px;
        font-size: 0.8em;
    }
</style>
</html>
