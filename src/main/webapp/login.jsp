<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="icon" type="image/x-icon" href='${pageContext.request.contextPath}/uploaded-images/favicon.ico'>
    <script src="https://kit.fontawesome.com/2bbac3a66c.js" crossorigin="anonymous"></script>
</head>

<body>
<style>
    .s-error {
        color: red;
        position: absolute;
        top: 205px;
        margin-bottom: 0;
        font-size: 15px;
    }

    /*.s-error {*/
    /*    color: red;*/
    /*    position: absolute;*/
    /*    bottom: 5px;*/
    /*    margin-top: 50px;*/
    /*    font-size: 15px;*/
    /*}*/

    .s-success {
        color: rgb(138, 255, 64);
        position: absolute;
        top: 205px;
        margin-bottom: 0;
        font-size: 15px;
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
<form id="login-form" action="controller" method="POST">
    <input type="hidden" name="action" value="login">
    <%
        if (session.getAttribute("successMSG") != null) { %>
    <div class="s-success">Registered successfully!! you can log in</div>
    <%
            session.removeAttribute("successMSG");
        }
    %>
    <%
        if (session.getAttribute("errorMessage1") != null) { %>
    <div style="font-size: 13px;" class="s-error">Wrong password has been provided, please try again</div>
    <%
            session.removeAttribute("errorMessage1");
        }
    %>
    <%
        if (session.getAttribute("errorMessage2") != null) { %>
    <div class="s-error">User Account does not exist, try register!!!</div>
    <%
            session.removeAttribute("errorMessage2");
        }
    %>
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
<!-- Error Modal -->
<div class="modal fade" id="errorModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Login Error</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <!-- Error message will be inserted here -->
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/scripts/login.js" type="text/javascript"></script>
<script>
    // setTimeout(function(){
    //     location.reload();
    // },3000);

</script>
</html>