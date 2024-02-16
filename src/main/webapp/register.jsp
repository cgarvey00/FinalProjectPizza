<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
    <input type="hidden" name="action" value="register">
    <div class="title" style="text-align: center;">
        <h2>Register as an Employee or Customer</h2>
        <h6 style="text-transform: none;">Choose a Strong password, at least 8 characters, one uppercase letter,
            lowercase, digit and special character</h6>
    </div>

    <!-- USERNAME -->
    <div class="input-group">
        <c:if test="${not empty sessionScope.usmg}">
            <div class="s-error">${sessionScope.usmg}</div>
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
        <input type="number" id="number" placeholder="5654667876" name="phoneNumber">
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
    <!-- USERTYPE -->
    <div class="form-outline form-white mb-4">
        <label style="color:#fff;" for="usertype">User Type</label>
        <select name="userType" id="usertype" class="required form-control form-control-lg"
                onchange="enableSubmit()">
            <option value="Customer">Customer</option>
            <option value="Employee">Employee </option>

            <i class="fas fa-check-circle"></i>
            <i class="fas fa-exclamation-circle"></i>
            <p>Error Message</p>
        </select>
    </div>
    <button type="submit" name="register" class="btn">Register</button>
    <br><br> <br>
    <div class="card-footer">
        <div style="color:#fff;" class="d-flex justify-content-center links">
            Already have an account?<a style="color:#6b1a1a;" href="controller?action=view-login">Log In</a>
        </div>
        <div style="color:#fff;" class="d-flex justify-content-center">
            <a style="color:#6b1a1a;" href="controller?action=home">Return Home</a>
        </div>
    </div>
</form>
</body>
<script src="${pageContext.request.contextPath}/scripts/validation.js" type="text/javascript"></script>

</html>