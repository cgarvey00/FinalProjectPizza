<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page import="com.finalprojectcoffee.entities.Order" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
    @SuppressWarnings("unchecked")
    List<Product> productList = (List<Product>) request.getSession().getAttribute("productList");
    @SuppressWarnings("unchecked")
    List<User> userList = (List<User>) request.getSession().getAttribute("userList");
    @SuppressWarnings("unchecked")
    List<Order> orderList = (List<Order>) request.getSession().getAttribute("orderList");

    boolean isError = (boolean) (request.getSession().getAttribute("errorPDF") != null ? request.getSession().getAttribute("errorPDF") : false);
    //
    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || productList == null || loggedInUser == null || !"Admin".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }


%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="icon" type="image/x-icon" href='${pageContext.request.contextPath}/uploaded-images/favicon.ico'>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/customer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
    <%--    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">--%>
    <!-- AJAX Link -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"></script>
</head>

<body>
<style>
    .message {
        position: fixed;
        top: 0;
        max-width: 1200px;
        margin: 0 auto;
        background-color: var(--light-bg);
        padding: 2rem;
        display: flex;
        align-items: center;
        justify-content: space-between;
        gap: 1.5rem;
        z-index: 1000;
    }
</style>
<%@include file="admin-nav.jsp" %>
<section class="dashboard">
    <%
        if (isError) {
    %>
    <div class="message">
        <span>Oops the Link must be broken, try it again</span>
        <i class="fas fa-times" onclick="this.parentElement.remove();"></i>
    </div>
    <%
        }
    %>
    <br>
    <br>
    <br>
    <br>
    <h1 class="heading">Admin Dashboard</h1>
    <div class="box-container">
        <!-- ADMIN  NAME AND WELCOME-->
        <div class="box">
            <h3>Welcome!</h3>
            <%
                if (loggedInUser != null) { %>
            <p><%=loggedInUser.getUsername() %>
            </p>
            <a style="font-size:13px;" class="btn"><%=loggedInUser.getEmail() %>
            </a>
            <%}%>
        </div>
        <!-- NUMBER OF TOTAL ORDERS-->
        <div class="box">
            <h3><%=orderList.size()%>
            </h3>
            <p>orders placed</p>
            <a href="controller?action=view-orders" class="btn">see orders</a>
        </div>

        <!-- NUMBER OF PENDING ORDERS-->
        <div class="box">
            <h3><%=orderList.size()%>
            </h3>
            <p>pending orders placed</p>
            <a href="controller?action=view-orders" class="btn">see orders</a>
        </div>

        <div class="box">
            <h3><%=productList.size()%>
            </h3>
            <p>products added</p>
            <a href="controller?action=view-products" class="btn">see products</a>
        </div>

        <!-- NUMBER OF USERS-->
        <div class="box">
            <h3><%=userList.size()%>
            </h3>
            <p>Users Present</p>
            <a href="controller?action=view-users" class="btn">see users</a>
        </div>
        <!-- PDF POPULAR PRODUCT-->
        <div class="box">
            <form action="controller" method="get">
                <input type="hidden" name="action" value="generate-popular-product-pdf">
                <h3>1</h3>
                <p>Weekly Popular Product</p>
                <button type="submit" class="btn"> Download</button>
            </form>
        </div>
        <!-- PDF POPULAR PRODUCT-->
        <div class="box">
            <form action="controller" method="get">
                <input type="hidden" name="action" value="generate-popular-product-pdf">
                <h3>1</h3>
                <p>Weekly Popular Product</p>
                <button type="submit" class="btn"> Download</button>
            </form>
        </div>
        <!-- PDF POPULAR PRODUCT-->
        <div class="box">
            <form action="controller" method="get">
                <input type="hidden" name="action" value="generate-popular-product-pdf">
                <h3>1</h3>
                <p>Weekly Popular Product</p>
                <button type="submit" class="btn"> Download</button>
            </form>
        </div>
        <!-- PDF POPULAR PRODUCT-->
        <div class="box">
            <form action="controller" method="get">
                <input type="hidden" name="action" value="generate-popular-product-pdf">
                <h3>1</h3>
                <p>Weekly Popular Product</p>
                <button type="submit" class="btn"> Download</button>
            </form>
        </div>
        <!-- PDF POPULAR PRODUCT-->
        <div class="box">
            <form action="controller" method="get">
                <input type="hidden" name="action" value="generate-popular-product-pdf">
                <h3>1</h3>
                <p>Weekly Popular Product</p>
                <button type="submit" class="btn"> Download</button>
            </form>
        </div>
        <!-- PDF POPULAR PRODUCT-->
        <div class="box">
            <form action="controller" method="get">
                <input type="hidden" name="action" value="generate-popular-product-pdf">
                <h3>1</h3>
                <p>Weekly Popular Product</p>
                <button type="submit" class="btn"> Download</button>
            </form>
        </div>
        <!-- PDF POPULAR PRODUCT-->
        <div class="box">
            <form action="controller" method="get">
                <input type="hidden" name="action" value="generate-popular-product-pdf">
                <h3>1</h3>
                <p>Weekly Popular Product</p>
                <button type="submit" class="btn"> Download</button>
            </form>
        </div>
    </div>

</section>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const navLinks = document.querySelector('a');
        navLinks.forEach(link => {
            link.addEventListener('click', () => {
                $.post('controller', {
                    action: 'sessionManagement',
                    task: 'clearAttribute',
                    attributeName: 'errorPDF'
                }, function (response) {
                    console.log(response);
                });
            });
        });
    });
</script>
<%@include file="footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
</html>