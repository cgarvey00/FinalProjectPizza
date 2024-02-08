<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>

<
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
          integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <!-- custom css file link  -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/stylesheets/signup.css">
</head>
<body>
<style>

    html,
    body {
        background-image: url('${pageContext.request.contextPath}/images/register.jpg');
        background-size: cover;
        background-repeat: no-repeat;
        font-family: 'Numans', sans-serif;
    }

    .error {
        color: rgb(186, 216, 255);
        font-weight: bold;
        position: absolute;
        bottom: 35px;
        margin-top: 50px;
        font-size: 15px;
    }
</style>
<section class="vh-150 gradient-custom">
    <div class="container py-560 h-160">
        <div class="row d-flex justify-content-center align-items-center h-150">
            <div class="col-12 col-md-12 col-lg-6 col-xl-5">
                <div class="card-body p-5 text-center">
                    <div class="mb-md-5 mt-md-4 pb-5">
                        <div class="d-flex justify-content-center h-100">
                            <div class="card py-560 h-170">
                                <div class="card-header">
                                    <h3>Sign Up Now</h3>
                                </div>
                                <div class="card-body">
                                    <form action="controller" method="post">
                                        <input type="hidden" name="action" value="register">
                                        <div class="input-group form-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                                            </div>
                                            <c:if test="${not empty sessionScope.emsg}">
                                                <div class="error">${sessionScope.emsg}</div>
                                            </c:if>
                                            <input type="email" class="required form-control " onkeyup="enableSubmit()"
                                                   name="email" placeholder="Email Address">
                                        </div>
                                        <div class="input-group form-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-users "></i></span>
                                            </div>
                                            <c:if test="${not empty sessionScope.usmg}">
                                                <div class="error">${sessionScope.usmg}</div>
                                            </c:if>
                                            <input type="text" class="required form-control " onkeyup="enableSubmit()"
                                                   name="username" placeholder="Username">
                                        </div>
                                        <div class="input-group form-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-key"></i></span>
                                            </div>
                                            <c:if test="${not empty sessionScope.pwvmsg}">
                                                <div class="error">${sessionScope.pwvmsg}</div>
                                            </c:if>
                                            <input type="password" class="required form-control "
                                                   onkeyup="enableSubmit()" name="password" placeholder="Password">
                                        </div>
                                        <div class="input-group form-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-key"></i></span>
                                            </div>
                                            <c:if test="${not empty sessionScope.pwcmsg}">
                                                <div class="error">${sessionScope.pwcmsg}</div>
                                            </c:if>
                                            <input type="password" class="required form-control "
                                                   onkeyup="enableSubmit()" name="passwordConfirmation"
                                                   placeholder="Confirm Password">
                                        </div>
                                        <div class="input-group form-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fad fa-phone"></i></span>
                                            </div>
                                            <c:if test="${not empty sessionScope.pnmsg}">
                                                <div class="error">${sessionScope.pnmsg}</div>
                                            </c:if>
                                            <input type="number" class="required form-control " onkeyup="enableSubmit()"
                                                   name="phoneNumber" placeholder="Phone Number">
                                        </div>
                                        <div class="form-group">
                                            <input type="submit" name="register" value="Sign up" disabled
                                                   class="btn float-right login_btn">
                                        </div>
                                    </form>
                                </div>
                                <div class="card-footer">
                                    <div class="d-flex justify-content-center links">
                                        Already have an account?<a href="controller?action=view-login">Log In</a>
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