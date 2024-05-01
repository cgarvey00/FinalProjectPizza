<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
    @SuppressWarnings("unchecked")
    List<Product> productList = (List<Product>) request.getSession().getAttribute("productList");

    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || productList == null || loggedInUser == null || !"Admin".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }
%>
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
<section class="dashboard">
    <br>br><br><br><br><br><br><br>
    <h1 class="heading">Admin Dashboard</h1>
    <div class="box-container">
        <!-- ADMIN  NAME AND WELCOME-->
        <div class="box">
            <h3>Welcome!</h3>
            <%
                if (loggedInUser != null) { %>
            <p style="font-size: 16px"><%=loggedInUser.getUsername() %></p>
            <p style="font-size: 18px"><%=loggedInUser.getEmail() %></p>
            <%}%>
        </div>
        <!-- NUMBER OF TOTAL ORDERS-->
        <div class="box">
            <c:choose>
                <c:when test="${not empty sessionScope.orderListByToday}">
                    <h3><c:out value="${sessionScope.orderListByToday.size()}"/></h3>
                </c:when>
                <c:otherwise>
                    <h3><c:out value="0"/></h3>
                </c:otherwise>
            </c:choose>
            <p style="font-size: 16px">Handle Order</p>
            <a href="controller?action=view-orderList-admin-today" class="btn" style="font-size: 18px">see orders</a>
        </div>
        <!-- NUMBER OF TOTAL EMPLOYEES-->
        <div class="box">
            <c:choose>
                <c:when test="${not empty sessionScope.employeeList}">
                <h3><c:out value="${sessionScope.employeeList.size()}"/></h3>
                </c:when>
                <c:otherwise>
                    <h3><c:out value="0"/></h3>
                </c:otherwise>
            </c:choose>
            <p style="font-size: 16px">Employees Present</p>
            <a href="controller?action=view-all-employees" class="btn" style="font-size: 18px">see employees</a>
        </div>
        <!-- NUMBER OF TOTAL PRODUCTS-->
        <div class="box">
            <c:choose>
                <c:when test="${not empty sessionScope.productList}">
                    <h3><c:out value="${sessionScope.productList.size()}"/></h3>
                </c:when>
                <c:otherwise>
                    <h3><c:out value="0"/></h3>
                </c:otherwise>
            </c:choose>
            <p style="font-size: 16px">Handle Product</p>
            <a href="controller?action=view-products" class="btn" style="font-size: 18px">see products</a>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
</html>