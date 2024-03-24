<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page import="com.finalprojectcoffee.entities.Address" %>

<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || loggedInUser == null || !"Customer".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }
    Address addressEcho = (Address) session.getAttribute("addressEcho");
%>

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
<h1 class="heading">Update Address</h1>
<% if(addressEcho != null && addressEcho.getIsDefault() == 0) { %>
<form action="controller" method="POST">
    <button type="submit" name="action" value="update-default-address" class="default-btn">Set Default</button>
</form>
<br>
<% } %>
<section class="add-products" style="position: relative;">
    <form action="controller" method="POST">
        <div class="flex">
            <div class="inputBox">
                <span>Street</span>
                <input type="text" class="box"  maxlength="100" placeholder="<%= addressEcho != null ? addressEcho.getStreet() : "Enter Street" %>" name="street">
            </div>
            <div class="inputBox">
                <span>Town</span>
                <input type="text" class="box"  maxlength="100" placeholder="<%= addressEcho != null ? addressEcho.getTown() : "Enter Town" %>" name="town">
            </div>
            <div class="inputBox">
                <span>County</span>
                <input type="text" minlength="0" class="box"  maxlength="100" placeholder="<%= addressEcho != null ? addressEcho.getCounty() : "Enter County" %>"
                       onkeypress="if(this.value.length === 20) return false;" name="county">
            </div>
            <div class="inputBox">
                <span>EirCode</span>
                <input name="eirCode" type="text" placeholder="<%= addressEcho != null ? addressEcho.getEirCode() : "Enter EirCode" %>" class="box" maxlength="100">
            </div>
        </div>
        <br>
        <button type="submit" name="action" value="update-address" class="update-btn">Update</button>
    </form>
</section>

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
        padding: 10px 295px;
        font-size: 18px;
        border: none;
        cursor: pointer;
        min-width: 60px;
        border-radius: 5px;
        display: block;
        margin: 0 auto;
    }

    .update-btn:hover {
        background-color: #017fbd;
    }

    .default-btn {
        background-color: #4CAF50;
        color: white;
        padding: 4px 8px;
        font-size: 14px;
        border: none;
        cursor: pointer;
        min-width: 60px;
        border-radius: 5px;
        position: absolute;
        right: 286px;
    }

    .default-btn:hover {
        background-color: #0c8612;
    }
</style>
</html>
