<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order</title>

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
<h1 style="text-align: center;">Change Password</h1>
<c:if test="${not empty sessionScope.loggedInUser}">
    <div class="show-products">
        <div class="box-container">
            <div class="box bg-light">
                <div class="content text-dark" style="text-align: center;">
                    <form action="controller" method="post">
                        <h2 style="text-align: center;">Old Password</h2>
                        <label>
                            <input type="password" name="oldPassword" required
                                   style="width: 200px; height: 25px; font-size: 16px; border: 1px solid black;"
                                   placeholder="Enter old password">
                        </label><br>
                        <span style="background-color: red; color: white; font-size: 15px;"><c:out
                                value="${sessionScope.opiMessage}"/></span>
                        <br>
                        <h2 style="text-align: center;">New Password</h2>
                        <label>
                            <input type="password" name="newPassword" required
                                   style="width: 200px; height: 25px; font-size: 16px; border: 1px solid black;"
                                   placeholder="Enter new password">
                        </label><br>
                        <span style="background-color: red; color: white; font-size: 15px;"><c:out
                                value="${sessionScope.pwMessage}"/></span>
                        <br>
                        <h2 style="text-align: center;">Confirm Password</h2>
                        <label>
                            <input type="password" name="passwordConfirmation" required
                                   style="width: 200px; height: 25px; font-size: 16px; border: 1px solid black;"
                                   placeholder="Confirm password">
                        </label><br>
                        <span style="background-color: red; color: white; font-size: 15px;"><c:out
                                value="${sessionScope.pwcMessage}"/></span>
                        <div style="text-align: center;">
                            <button type="submit" name="action" value="change-password" class="change-btn">Change
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</c:if>

<jsp:include page="footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>


<c:if test="${not empty sessionScope.opiMessage}">
    <c:remove var="opiMessage" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.pwMessage}">
    <c:remove var="pwMessage" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.pwcMessage}">
    <c:remove var="pwcMessage" scope="session"/>
</c:if>
</body>

<style>
    .change-btn {
        background-color: #109acb;
        color: white;
        padding: 5px 20px;
        font-size: 18px;
        border: none;
        cursor: pointer;
        margin-top: 30px;
        min-width: 60px;
        border-radius: 5px;
    }

    .change-btn:hover {
        background-color: #017fbd;
    }
</style>
</html>
