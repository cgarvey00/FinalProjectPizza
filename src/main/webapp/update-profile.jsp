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
<c:choose>
    <c:when test="${sessionScope.userType == 'customer'}">
        <jsp:include page="customer-nav.jsp"/>
    </c:when>
    <c:when test="${sessionScope.userType == 'admin'}">
        <jsp:include page="admin-nav.jsp"/>
    </c:when>
    <c:when test="${sessionScope.userType == 'employee'}">
        <jsp:include page="employee-nav.jsp"/>
    </c:when>
</c:choose>
<br><br><br><br><br><br><br><br><br><br>
<h1 class="heading">Update Profile</h1>
<c:if test="${not empty sessionScope.loggedInUser}">
<section class="add-products">
    <form action="controller" method="POST">
        <div class="flex">
            <div class="inputBox">
                <span>Username (Read Only)</span>
                <input type="text" class="box" readonly maxlength="100" placeholder="${sessionScope.loggedInUser.getUsername()}">
            </div>
            <div class="inputBox">
                <span>Phone Number</span>
                <input type="text" class="box" maxlength="100" placeholder="${sessionScope.loggedInUser.getPhoneNumber()}" name="phoneNumber">
            </div>
            <div class="inputBox">
                <span>Email</span>
                <input type="text" class="box" maxlength="100" placeholder="${sessionScope.loggedInUser.getEmail()}" name="email">
            </div>
            <div class="inputBox">
                <span>User Type (Read Only)</span>
                <input type="text" class="box" readonly placeholder="${sessionScope.loggedInUser.getUserType()}" maxlength="100">
            </div>
        </div>
        <br>
        <button type="submit" name="action" value="update-user-profile" class="update-btn">Update</button>
    </form>
</section>
</c:if>

<jsp:include page="footer.jsp"/>

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
</style>
</html>
