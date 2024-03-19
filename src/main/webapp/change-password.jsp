<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

//    boolean addressed = (boolean) request.getSession().getAttribute("addressed");
    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || loggedInUser == null || !"Customer".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }

%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/signup.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
          integrity="sha512-Fo3rlrZj/k7ujTnHg4CGR2D7kSs0v4LLanw2qksYuRlEzO+tcaEPQogQ0KaoGN26/zrn20ImR1DfuLWnOo7aBA=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />

    <script src="https://kit.fontawesome.com/2bbac3a66c.js" crossorigin="anonymous"></script>
</head>

<body>
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
<form id="register-form" action="controller" method="POST">
    <input type="hidden" name="action" value="change-password">
    <div class="title" style="text-align: center;">
        <h2>Reset Your Password</h2>
        <h6 style="text-transform: none;">Choose a Strong password, at least 8 characters, one uppercase letter,
            lowercase, digit and special character</h6>
    </div>
    <!-- PASSWORD -->
    <div class="input-group">
        <label for="password">Change Password</label>
        <input type="password" id="password" placeholder="Password" name="newPassword">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p>Error Message</p>
    </div>
    <!-- CONFIRM PASSWORD -->
    <div class="input-group">
        <label for="confirm-password">Confirm Password</label>
        <input type="password" id="confirm-password" placeholder="Password" name="passwordConfirmation">
        <i class="fas fa-check-circle"></i>
        <i class="fas fa-exclamation-circle"></i>
        <p>Error Message</p>
    </div>
    <button type="submit" name="change-password" class="btn">Confirm</button>
    <br><br> <br>
    <div class="card-footer">
        <div style="color:#fff;" class="d-flex justify-content-center">
            <a style="color:#6b1a1a;" href="../">Go Back</a>
        </div>
    </div>
</form>
</body>
<script src="${pageContext.request.contextPath}/scripts/validation.js" type="text/javascript"></script>
</html>
