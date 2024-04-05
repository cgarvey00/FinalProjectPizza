<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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
<h1 class="heading">Employee Update</h1>
<section class="add-products">
    <form action="controller" method="POST">
        <div class="flex">
            <div class="inputBox">
                <span>Name</span>
                <c:if test="${not empty sessionScope.umsg}">
                    <div class="s-error">${sessionScope.umsg}</div>
                </c:if>
                <input type="text" class="box" required maxlength="100" placeholder="${sessionScope.employee.getUsername()}" name="username">
            </div>
            <div class="inputBox">
                <span>Phone Number</span>
                <c:if test="${not empty sessionScope.pnmsg}">
                    <div class="s-error">${sessionScope.pnmsg}</div>
                </c:if>
                <input type="text" class="box" required maxlength="100" placeholder="${sessionScope.employee.getPhoneNumber()}" name="phoneNumber">
            </div>
            <div class="inputBox">
                <span>Email</span>
                <c:if test="${not empty sessionScope.emsg}">
                    <div class="s-error">${sessionScope.emsg}</div>
                </c:if>
                <input type="text" class="box" required maxlength="100" placeholder="${sessionScope.employee.getEmail()}" name="email">
            </div>
            <div class="inputBox">
                <span>Password</span>
                <c:if test="${not empty sessionScope.pwvmsg}">
                    <div class="s-error">${sessionScope.pwvmsg}</div>
                </c:if>
                <input type="password" class="box" required maxlength="100" placeholder="Enter Password" name="password">
            </div>
            <div class="inputBox">
                <span>Password Confirmation</span>
                <c:if test="${not empty sessionScope.pwcmsg}">
                    <div class="s-error" style="right: 0; top: 0;">${sessionScope.pwcmsg}</div>
                </c:if>
                <input type="password" class="box" required maxlength="100" placeholder="Enter Password Again" name="passwordConfirmation">
            </div>
        </div>
        <br>
        <button type="submit" name="action" value="register" class="add-btn">Update</button>
        <input type="hidden" name="userType" value="Employee">
    </form>
</section>

<c:if test="${not empty sessionScope.umsg}">
    <c:remove var="umsg" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.emsg}">
    <c:remove var="emsg" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.pnmsg}">
    <c:remove var="pnmsg" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.pwvmsg}">
    <c:remove var="pwvmsg" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.pwcmsg}">
    <c:remove var="pwcmsg" scope="session"/>
</c:if>

<jsp:include page="footer.jsp"/>
</body>
<script src="${pageContext.request.contextPath}/scripts/validation.js" type="text/javascript"></script>

<style>
    .add-btn {
        background-color: #109acb;
        color: white;
        padding: 10px 290px;
        font-size: 18px;
        border: none;
        cursor: pointer;
        min-width: 60px;
        border-radius: 5px;
        display: block;
        margin-top: 20px;
    }

    .add-btn:hover {
        background-color: #017fbd;
    }

    .inputBox {
        position: relative;
    }

    .s-error {
        position: absolute;
        top: 0;
        right: 0;
        background: #fff;
        color: #ff4d4d;
        font-weight: bold;
        font-size: 15px;
        padding: 0 5px;
        border-radius: 3px;
        z-index: 5;
    }
</style>

</html>