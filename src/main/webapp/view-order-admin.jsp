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
<jsp:include page="admin-nav.jsp"/>
<br><br><br><br><br><br><br><br><br><br>
<div class="box-container">
    <h1 class="heading">Order List</h1>
    <c:if test="${not empty sessionScope.orderList}">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Create Time</th>
                <th>Update Time</th>
                <th>Status</th>
                <th>Payment Status</th>
                <th>Customer ID</th>
                <th style="width: 150px">Employee</th>
                <th>Address</th>
                <th>Balance</th>
                <th class="action-column">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${sessionScope.orderList}" varStatus="status">
                <tr>
                    <td><c:out value="${order.getId()}"/></td>
                    <td>${my:formatLocalDateTime(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss")}</td>
                    <td>${my:formatLocalDateTime(order.getUpdateTime(), "yyyy-MM-dd HH:mm:ss")}</td>
                    <td><c:out value="${order.getStatus()}"/></td>
                    <td><c:out value="${order.getPaymentStatus()}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty order.getCustomer()}">
                                <c:out value="${order.getCustomer().getId()}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="Deletd Customer"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="width: 150px">
                        <c:choose>
                            <c:when test="${order.getStatus() != 'Finished' && order.getStatus() != 'Cancelled'}">
                                    <label>
                                        <select id="employeeSelect${status.index}" name="selectedEmployeeId"
                                                data-initial-id="${order.getEmployee().getId()}"
                                                onchange="checkEmployeeId(${status.index})">
                                            <option value="${order.getEmployee().getId()}" selected>
                                                <c:choose>
                                                    <c:when test="${order.getEmployee() != null}">
                                                        <c:out value="${order.getEmployee().getUsername()}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        Select
                                                    </c:otherwise>
                                                </c:choose>
                                            </option>
                                            <c:if test="${not empty sessionScope.employeeList}">
                                                <c:forEach var="employee" items="${sessionScope.employeeList}">
                                                    <c:if test="${employee.getStatus() == 'Available' && employee.getId() ne order.getEmployee().getId()}">
                                                        <option value="${employee.getId()}"><c:out
                                                                value="${employee.getUsername()}"/></option>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                    </label>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${order.getEmployee().getUsername()}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${order.getAddress().getEirCode()}"/></td>
                    <td><c:out value="${order.getBalance()}"/>&euro;</td>
                    <td>
                        <form action="controller" method="post">
                            <button type="submit" name="action" value="view-order" class="detail-btn">Detail</button>
                            <button type="submit" name="action" value="update-employee-in-order"
                                    id="update-btn${status.index}" class="update-btn" style="display: none">
                                Update
                            </button>
                            <input type="hidden" id="selectedEmployeeId${status.index}" name="selectedEmployeeId"/>
                            <input type="hidden" name="orderId" value="${order.getId()}"/>
                            <input type="hidden" name="userType" value="admin">
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
    //Check employee id and show update button if it's changed
    function checkEmployeeId(index) {
        if (index >= 0) {
            var initialEmployeeId = document.getElementById('employeeSelect' + index).getAttribute('data-initial-id');
            var selectedEmployeeId = document.getElementById('employeeSelect' + index).value;
            var updateBtn = document.getElementById('update-btn' + index);
            var detailBtn = document.getElementsByClassName('detail-btn')[index];
            var hiddenInput = document.getElementById('selectedEmployeeId' + index);

            hiddenInput.value = selectedEmployeeId;

            if (initialEmployeeId !== selectedEmployeeId) {
                updateBtn.style.display = 'inline';
                detailBtn.style.display = 'none';
            } else {
                updateBtn.style.display = 'none';
                detailBtn.style.display = 'inline';
            }
        } else {
            console.error('Invalid index value');
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

    .detail-btn, .update-btn {
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

    .detail-btn {
        background-color: #017fbd;
    }

    .detail-btn:hover {
        background-color: #017fbd;
    }

    .update-btn {
        background-color: #4CAF50;
    }

    .update-btn:hover {
        background-color: #3e8e41;
    }
</style>
</html>
