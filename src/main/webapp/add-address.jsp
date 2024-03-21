<%@ page import="com.finalprojectcoffee.entities.User" %>

<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
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
<h1 class="heading">Add Address</h1>
<section class="add-products">
    <form action="controller" method="POST">
        <input type="hidden" name="action" value="add-address">
        <input type="hidden" name="uID" value="<%=loggedInUser.getId()%>">
        <div class="flex">
            <div class="inputBox">
                <span>Street (required)</span>
                <input type="text" class="box" required maxlength="100" placeholder="Enter Street" name="street">
            </div>
            <div class="inputBox">
                <span>Town (required)</span>
                <input type="text" class="box" required maxlength="100" placeholder="Enter Town" name="town">
            </div>
            <div class="inputBox">
                <span>County (required)</span>
                <input type="text" minlength="0" class="box" required maxlength="100" placeholder="Enter County"
                       onkeypress="if(this.value.length === 20) return false;" name="county">
            </div>
            <div class="inputBox">
                <span>EirCode</span>
                <textarea name="eirCode" type="text" placeholder="Enter EirCode" class="box" required
                          maxlength="500"
                          cols="30" rows="10"></textarea>
            </div>
        </div>
        <input type="submit" value="add address" class="btn" name="add-address">
    </form>
</section>

<%@include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
</html>
