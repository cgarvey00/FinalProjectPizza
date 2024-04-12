<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<!-- home section starts  -->
<section class="products" style="padding-top: 0; min-height:100vh;">
    <div class="box-container">
        <h2 class="heading">Employee List</h2>
        <div class="button-container">
            <form action="controller" method="post">
                <button type="submit" name="action" value="set-all-employees-available" class="all-available-btn">All Available</button>
                <button type="submit" name="action" value="set-all-employees-unavailable" class="all-unavailable-btn">All Unavailable</button>
            </form>
            <form action="employee-register.jsp" method="get">
                <button type="submit" class="add-employee-btn">Add</button>
            </form>
        </div>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th style="width: 60px">ID</th>
                <th>Name</th>
                <th>Phone Number</th>
                <th style="width: 230px">Email</th>
                <th>Status</th>
                <th class="action-column">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty sessionScope.employeeList}">
                <c:forEach var="employee" items="${sessionScope.employeeList}">
                    <tr>
                        <td style="width: 60px"><c:out value="${employee.getId()}"/></td>
                        <td><c:out value="${employee.getUsername()}"/></td>
                        <td><c:out value="${employee.getPhoneNumber()}"/></td>
                        <td style="width: 230px"><c:out value="${employee.getEmail()}"/></td>
                        <td><c:out value="${employee.getStatus()}"/></td>
                        <td>
                            <form action="controller" method="post" class="action-buttons">
                                <c:choose>
                                    <c:when test="${employee.getStatus() == 'Unavailable'}">
                                        <button type="submit" name="action" value="switch-employee-status"
                                                class="available-btn">Available
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" name="action" value="switch-employee-status"
                                                class="unavailable-btn">
                                            Unavailable
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                                <input type="hidden" name="employeeId" value="${employee.getId()}"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
</section>

<jsp:include page="footer.jsp"/>

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
        width: 130px;
        max-width: 130px;
    }

    .button-container {
        display: flex;
        justify-content: space-between;
        margin-bottom: 5px;
    }

    .add-employee-btn, .all-available-btn, .all-unavailable-btn {
        color: white;
        padding: 8px 20px;
        font-size: 18px;
        border: none;
        cursor: pointer;
        min-width: 60px;
        border-radius: 5px;
        margin: 0 5px 5px 0;
        display: inline-block;
    }

    .add-employee-btn {
        background-color: #109acb;
    }

    .add-employee-btn:hover {
        background-color: #017fbd;
    }

    .all-available-btn {
        background-color: #4ce800;
    }

    .all-available-btn:hover {
        background-color: #3e8e41;
    }

    .all-unavailable-btn {
        background-color: #ff0000;
    }

    .all-unavailable-btn:hover {
        background-color: #c20000;
    }

    .available-btn, .unavailable-btn {
        padding: 5px 10px;
        color: white;
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

    .available-btn {
        background-color: #4ce800;
    }

    .available-btn:hover {
        background-color: #3e8e41;
    }

    .unavailable-btn {
        background-color: #ff0000;
    }

    .unavailable-btn:hover {
        background-color: #c20000;
    }
</style>
</html>