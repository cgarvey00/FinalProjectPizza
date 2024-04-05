<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/signup.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <script src="https://kit.fontawesome.com/2bbac3a66c.js" crossorigin="anonymous"></script>
</head>

<body>
<form id="register-form" action="controller" method="POST">
    <input type="hidden" name="action" value="register">
    <div class="title" style="text-align: center;">
        <h2>Register</h2>
        <h6 style="text-transform: none;">Choose a Strong password, at least 8 characters, one uppercase letter,
            lowercase, digit and special character</h6>
    </div>

    <!-- USERNAME -->
    <div class="input-group">
        <c:if test="${not empty sessionScope.umsg}">
            <div class="s-error">${sessionScope.umsg}</div>
        </c:if>
        <label for="username">Username</label>
        <input type="text" id="username" placeholder="Username" name="username">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p>Error Message</p>
    </div>
    <!-- PHONE -->
    <div class="input-group">
        <c:if test="${not empty sessionScope.pnmsg}">
            <div class="s-error">${sessionScope.pnmsg}</div>
        </c:if>
        <label for="number">Phone</label>
        <input type="text" id="number" placeholder="083XXXXXXX" name="phoneNumber">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p>Error Message</p>
    </div>
    <!-- EMAIL -->
    <div class="input-group">
        <c:if test="${not empty sessionScope.emsg}">
            <div class="s-error">${sessionScope.emsg}</div>
        </c:if>
        <label for="email">Email</label>
        <input type="email" id="email" placeholder="Email" name="email">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p>Error Message</p>
    </div>
    <!-- PASSWORD -->
    <div class="input-group">
        <c:if test="${not empty sessionScope.pwvmsg}">
            <div class="s-error">${sessionScope.pwvmsg}</div>
        </c:if>
        <label for="password">Password</label>
        <input type="password" id="password" placeholder="Password" name="password">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p>Error Message</p>
    </div>
    <!-- CONFIRM PASSWORD -->
    <div class="input-group">
        <c:if test="${not empty sessionScope.pwcmsg}">
            <div class="s-error">${sessionScope.pwcmsg}</div>
        </c:if>
        <label for="confirm-password">Confirm Password</label>
        <input type="password" id="confirm-password" placeholder="Password" name="passwordConfirmation">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p>Error Message</p>
    </div>
    <%--  Default user type: Customer  --%>
    <input type="hidden" name="userType" id="usertype" value="Customer">
    <button type="submit" name="register" class="btn">Register</button>
    <br><br> <br>
    <div class="card-footer">
        <div style="color:#fff;" class="d-flex justify-content-center links">
            Already have an account?<a style="color:#6b1a1a;" href="controller?action=view-login">Login</a>
        </div>
        <div style="color:#fff;" class="d-flex justify-content-center">
            <a style="color:#6b1a1a;" href="controller?action=home">Return Home</a>
        </div>
    </div>
</form>

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

</body>
<script src="${pageContext.request.contextPath}/scripts/validation.js" type="text/javascript"></script>

<style>
    body {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        background-color: rgb(0, 0, 0);
        background-image: url('${pageContext.request.contextPath}/images/pizza.jpg');
        background-size: cover;
        background-repeat: no-repeat;
    }

    .s-error {
        color: rgb(154, 50, 50);
        font-weight: bold;
        position: absolute;
        bottom: 25px;
        margin-top: 50px;
        font-size: 15px;
    }
</style>

</html>