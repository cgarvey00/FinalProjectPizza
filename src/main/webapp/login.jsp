<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- font awesome cdn link  -->
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <!-- font awesome cdn link  -->
    <!--Fontawesome CDN-->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon.ico">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
          integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <!-- custom css file link  -->
    <!--Custom styles-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/login.css">
</head>

<body>
<style>
    .error {
        position: absolute;
        bottom: 45px;
        color: rgb(186, 216, 255);
        font-weight: bold;
        font-size: 14px;
    }

    html,
    body {
        background-image: url('${pageContext.request.contextPath}/images/login.jpg');
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center;
        height: 100%;
        font-family: 'Numans', sans-serif;
    }
</style>
<section class="vh-100 gradient-custom">
    <div class="container py-500 h-100">
        <div class="row d-flex justify-content-center align-items-center h-140">
            <div class="col-12 col-md-10 col-lg-6 col-xl-5">
                <div class="card-body p-5 text-center">
                    <div class="mb-md-5 mt-md-4 pb-5">
                        <div class="d-flex justify-content-center h-50">
                            <div class="card  py-100 h-20">
                                <div class="card-header">
                                    <h3>Login</h3>
                                    <div class="card-body">
                                        <form action="controller" method="post">
                                            <input type="hidden" name="action" value="login">
                                            <div class="input-group form-group">
                                                <c:if test="${not empty sessionScope.errorMessage}">
                                                    <div class="error">${sessionScope.pnmsg}</div>
                                                </c:if>
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text"><i class="fas fa-user"></i></span>
                                                </div>
                                                <input type="text" class="required form-control "
                                                       onkeyup="enableSubmit()" name="username"
                                                       placeholder="Sample Username">
                                            </div>
                                            <div class="input-group form-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text"><i class="fas fa-key"></i></span>
                                                </div>
                                                <input type="password" name="password" class="required form-control"
                                                       onkeyup="enableSubmit()" placeholder="Password">
                                            </div>
                                            <div class="form-group">
                                                <input type="submit" value="Login" disabled name="login"
                                                       class="btn float-right login_btn">
                                            </div>
                                        </form>

                                    </div>
                                    <div class="card-footer">
                                        <div class="d-flex justify-content-center links">
                                            Don't have an account?<a href="controller?action=view-register">Sign Up</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
<script src="${pageContext.request.contextPath}/scripts/script.js" type="text/javascript"></script>

</html>