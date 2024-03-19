<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page import="com.finalprojectcoffee.entities.Address" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.finalprojectcoffee.repositories.UserRepositories" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

    boolean addressed = (boolean) request.getSession().getAttribute("addressed");
    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || loggedInUser == null || !"Customer".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }

%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Page</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/customer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">

</head>
<style>
    :root {
        --main-color: #2980b9;
        --orange: #f39c12;
        --red: #e74c3c;
        --black: #333;
        --white: #fff;
        --light-color: #666;
        --light-bg: #eee;
        --border: .2rem solid var(--black);
        --box-shadow: 0 .5rem 1rem rgba(0, 0, 0, .1);
    }

    .home {
        background: -webkit-gradient(linear, left top, left bottom, from(rgba(0, 0, 0, 0.7)), to(rgba(0, 0, 0, 0.7))), url('${pageContext.request.contextPath}/images/homebg.jpg') no-repeat;
        background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('${pageContext.request.contextPath}/images/homebg.jpg') no-repeat;
        background-size: cover;
        background-position: center;
        background-attachment: fixed;
    }


</style>
<body>
<%@include file="customer-nav.jsp" %>




<%@include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>

</html>