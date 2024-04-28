<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/customer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="icon" type="image/x-icon" href='${pageContext.request.contextPath}/uploaded-images/favicon.ico'>
</head>
<style>
    section {
        padding: 5rem 1rem;
    }

    :root {
        --main-color: #d3ad7f;
        --black: #13131a;
        --bg: #010103;
        --border: .1rem solid rgba(255, 255, 255, .3);
    }
    .home {
        background: -webkit-gradient(linear, left top, left bottom, from(rgba(0, 0, 0, 0.7)), to(rgba(0, 0, 0, 0.7))), url('${pageContext.request.contextPath}/images/homebg.jpg') no-repeat;
        background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('${pageContext.request.contextPath}/images/homebg.jpg') no-repeat;
        background-size: cover;
        background-position: center;
        background-attachment: fixed;
    }
    .contact .row {
        display: flex;
        background: #EAE7DC;
        flex-wrap: wrap;
        gap: 1rem;
    }

    .contact .row .map {
        flex: 1 1 45rem;
        width: 100%;
        object-fit: cover;
    }

    .contact .row form {
        flex: 1 1 45rem;
        padding: 5rem 2rem;
        text-align: center;
        background-color: #FFF8E9;
    }

    .contact .row form h3 {
        text-transform: uppercase;
        font-size: 3.5rem;
        color: #5C4033;
    }

    .contact .row form .inputBox {
        display: flex;
        align-items: center;
        margin-top: 2rem;
        margin-bottom: 2rem;
        background: #FCF7F1;
        border: #D8C3AC;
    }
    .contact .row form .inputBox input,
    .contact .row form .inputBox span {
        color: #fff;
        font-size: 2rem;
        padding-left: 2rem;
    }

    .contact .row form .inputBox input {
        width: 100%;
        padding: 2rem;
        font-size: 1.7rem;
        color: #000000;
        text-transform: none;
        background: none;        border: var(--bg);
    }


    .about span {
        font-size: 2.5rem;
        color: #691f1f;
    }

    .about .title {
        padding-top: 1rem;
        font-size: 3rem;
        text-transform: capitalize;
        color: #222;
    }

    .about p {
        padding: 1rem 0;
        font-size: 1.6rem;
        line-height: 2;
        color: #666;
        margin-bottom: 0;
    }

    .about .icons-container {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -ms-flex-wrap: wrap;
        flex-wrap: wrap;
        margin-top: 3rem;
        gap: 1.5rem;
    }

    .about .icons-container .icons {
        padding: 2rem;
        background: #eee;
        text-align: center;
        -webkit-box-flex: 1;
        -ms-flex: 1 1 14rem;
        flex: 1 1 14rem;
    }

    .about .icons-container .icons i {
        font-size: 4rem;
        margin-bottom: 1.5rem;
        color:#691f1f;
    }

    .about .icons-container .icons h3 {
        font-size: 1.5rem;
        text-transform: capitalize;
        color: #222;
    }

</style>
<body>
<%@include file="normal-nav.jsp" %>
<!-- home section starts  -->

<section class="home" id="home">

    <div class="container">

        <div class="row align-items-center text-center text-md-left min-vh-100">
            <div class="col-md-6">
                <span>Welcome to the Pizza Shop</span>
                <h3>Browse Our Menu </h3>
                <a href="controller?action=view-landing-menu" class="link-btn">Get Started</a>
            </div>
        </div>

    </div>

</section>
<!-- about section starts  -->

<section class="about" id="about">

    <div class="container">

        <div class="row align-items-center">
            <div class="col-md-6">
                <img src='${pageContext.request.contextPath}/images/aboutimagee.jpg' class="w-100 h-100" alt="">
            </div>
            <div class="col-md-6">
                <span>why choose us?</span>
                <h3 class="title">the best pizzeria the town</h3>
                <a href="#" class="link-btn">read more</a>
                <div class="icons-container">
                    <div class="icons">
                        <i class="fa-solid fa-pizza-slice"></i>
                        <h3>best pizza</h3>
                    </div>
                    <div class="icons">
                        <i class="fa-solid fa-burger"></i>
                        <h3>best burgers</h3>
                    </div>
                    <div class="icons">
                        <i class="fa-solid fa-ice-cream"></i>
                        <h3>tasty desserts</h3>
                    </div>
                    <div class="icons">
                        <i class="fa-solid fa-cookie"></i>
                        <h3>tasty sides</h3>
                    </div>
                    <div class="icons">
                        <i class="fas fa-shipping-fast"></i>
                        <h3>free delivery</h3>
                    </div>
                    <div class="icons">
                        <i class="fas fa-headset"></i>
                        <h3>24/7 service</h3>
                    </div>
                </div>
            </div>
        </div>

    </div>

</section>
<!-- about section ends  -->
<!-- contact section starts  -->

<section class="contact" id="contact">

    <h1 class="heading"><span>contact</span> us </h1>

    <div class="row">

        <iframe src="https://www.google.com/maps/d/embed?mid=1QEIiX1sdlQrHeIucuz60p-laZ8M&hl=en_US&ehbc=2E312F"
                width="640" height="480"></iframe>

        <form action="">
            <h3>get in touch</h3>
            <div class="inputBox">
                <span style="color: #010103;" class="fas fa-user"></span>
                <input type="text" placeholder="Full Name">
            </div>
            <div class="inputBox">
                <span style="color: #010103;" class="fas fa-envelope"></span>
                <input type="email" placeholder="Email">
            </div>
            <div class="inputBox">
                <span style="color: #010103;" class="fas fa-phone"></span>
                <input type="number" placeholder="Phone Number">
            </div>
            <input type="submit" value="contact now" class="option-btn">
        </form>

    </div>

</section>

<!-- contact section ends -->
<%@include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>

</html>