<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page import="com.finalprojectcoffee.entities.Order" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
    List<Product> productList = (List<Product>) request.getSession().getAttribute("productList");
    List<User> userList = (List<User>) request.getSession().getAttribute("eUserList");
    List<Order> orderList = (List<Order>) request.getSession().getAttribute("eOrderList");
    List<Order> freeOrders = (List<Order>) request.getSession().getAttribute("freeOrders");
    boolean isOrdersEmpty = false;
    if (freeOrders == null) {
        isOrdersEmpty = true;
    }

    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || productList == null || loggedInUser == null || !"Employee".equals(loggedInUser.getUserType())) {
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
    <link rel="icon" type="image/x-icon" href='${pageContext.request.contextPath}/uploaded-images/favicon.ico'>
    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>

<body>
<%@include file="employee-nav.jsp" %>
<section class="dashboard">
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <h1 class="heading">Employee Dashboard</h1>
    <div class="box-container">
        <!-- EMPLOYEE NAME AND WELCOME-->
        <div class="box">
            <h3>Welcome!</h3>
            <%
                if (loggedInUser != null) { %>
            <p><%=loggedInUser.getUsername() %>
            </p>
            <a style="font-size:13px;" class="btn"><%=loggedInUser.getEmail() %>
            </a>
            <%}%>
        </div>
        <!-- NUMBER OF EMPLOYEE ORDERS-->
        <div class="box">
            <h3><%=orderList.size()%>
            </h3>
            <p>Current Orders</p>
            <a href="controller?action=view-orders" class="btn">see orders</a>
        </div>
        <!-- NUMBER OF USERS-->
        <div class="box">
            <h3><%=userList.size()%>
            </h3>
            <p>Users Present</p>
            <a href="controller?action=view-orders" class="btn">see users</a>
        </div>
        <!-- NUMBER OF  ORDERS-->
        <div class="box">
            <h3><%=freeOrders.size() %></h3>
            <p>Orders to Deliver</p>
            <a href="controller?action=view-order-employee" class="btn">see free orders</a>
        </div>
    </div>
</section>
<%@include file="footer.jsp" %>
<script>

</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
</html>