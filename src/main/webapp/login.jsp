<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/login.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
          integrity="sha512-Fo3rlrZj/k7ujTnHg4CGR2D7kSs0v4LLanw2qksYuRlEzO+tcaEPQogQ0KaoGN26/zrn20ImR1DfuLWnOo7aBA=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />

    <script src="https://kit.fontawesome.com/2bbac3a66c.js" crossorigin="anonymous"></script>
</head>

<body>
<form id="login-form" action="controller" method="POST">
    <input type="hidden" name="action" value="login">
    <c:if test="${not empty sessionScope.successmsg}">
        <div class="s-success">${sessionScope.successmsg}</div>
    </c:if>

    <c:if test="${not empty sessionScope.errorMessage}">
        <div class="s-error">${sessionScope.errorMessage}</div>
    </c:if>
    <div class="title" style="text-align: center;">
        <h2>Login Here</h2>
    </div>
    <!-- USERNAME -->
    <div class="input-group">
        <label for="username">Username</label>
        <input type="text" id="username" placeholder="Username" name="username">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p>Error Message</p>
    </div>
    <!-- PASSWORD -->
    <div class="input-group">
        <label for="password">Password</label>
        <input type="password" id="password" placeholder="Password" name="password">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p>Error Message</p>
    </div>
    <button type="submit" name="login" class="btn">Login</button>
    <br><br> <br>
    <div class="card-footer">
        <div style="color:#fff;" class="d-flex justify-content-center links">
            Haven't got an account?<a style="color:#6b1a1a;" href="controller?action=view-register">Register</a>
        </div>
        <div style="color:#fff;" class="d-flex justify-content-center">
            <a style="color:#6b1a1a;" href="controller?action=home">Return Home</a>
        </div>
    </div>
</form>

<c:if test="${not empty sessionScope.errorMessage}">
    <c:remove var="errorMessage" scope="session"/>
</c:if>

</body>
<script src="${pageContext.request.contextPath}/scripts/login.js" type="text/javascript"></script>

<style>
    .s-error {
        color: rgb(243, 74, 74);
        font-weight: bold;
        position: absolute;
        top: 205px;
        margin-bottom: 0;
        font-size: 13.5px;
    }

    .s-success {
        color: rgb(138, 255, 64);
        font-weight: bold;
        position: absolute;
        top: 165px;
        margin-bottom: 0;
        font-size: 13.5px;
    }
    body {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        background-color: rgb(0, 0, 0);
        background-image: url('${pageContext.request.contextPath}/images/login.jpg');
        background-size: cover;
        background-repeat: no-repeat;
    }
</style>

</html>