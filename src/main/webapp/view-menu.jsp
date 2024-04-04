<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <!-- ajax link  -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <!-- bootstrap cdn link  -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>
<body>
<%@include file="customer-nav.jsp"%>
<!-- home section starts  -->
<br><br><br><br><br><br><br><br><br><br>
<div id="toast-container" class="toast-container"></div>
<div class="show-products" id="products">
    <h1 style="text-align: center;">View Products On Menu</h1>
    <div class="box-container">
        <%
            @SuppressWarnings("unchecked")
            List<Product> productList = (List<Product>) request.getSession().getAttribute("productList");
            if (productList != null && !productList.isEmpty()) {
                for (Product product : productList) { %>
        <form action="controller" method="POST">
            <div class="box bg-light">
                <div class="image">
                    <img src="${pageContext.request.contextPath}/images/<%= product.getImage() %>" alt="image">
                </div>
                <div class="content text-dark">
                    <h3 class="text-dark">
                        <%=product.getName()%>
                    </h3>
                    <h3 class="text-dark" style="font-size:10px;">
                        <%=product.getDetails()%>
                    </h3>
                    <div class="price">
                        <p class="text-dark">&euro;
                            <%=String.format("%.2f", product.getPrice())  %>
                        </p>
                    </div>
                    <h4 class="text-dark">Place cart quantity</h4>
                    <label>
                        <input type="number" id="quantity<%= product.getId() %>" name="quantity" style="font-size:15px;" required class="box" min="1" max="<%= product.getStock() %>" placeholder="enter quantity" value="1"<% if (product.getStock() == 0) { %> disabled <% }%> >
                    </label>
                    <% if (product.getStock() > 0) { %>
                    <button type="button" name="action" class="cart-btn" onclick="addToCart(<%= product.getId() %>)">Add to Cart</button>
                    <% } else { %>
                    <button type="button" name="action" class="cart-btn out-of-stock" disabled>Out of Stock</button>
                    <% } %>
                </div>
            </div>
        </form>
        <% } %>
    </div>
    <% } else { %>
    <p class="empty">No Products Added Yet!</p>
    <% } %>
</div>

<script>
    function addToCart(productId){
        var quantity = document.getElementById("quantity" + productId).value
        $.ajax({
            url: 'controller',
            type: 'POST',
            data: {
                ajax: true,
                action: 'add-to-cart',
                productId: productId,
                quantity: quantity
            },
            success: function (response){
                if(response.success) {
                    showToast(response.addCartMessage);
                } else {
                    showToast(response.addCartMessage, true);
                }
            }
        })
    }

    function showToast(message, isError) {
        var toast = document.createElement("div");
        toast.className = "toast-message";
        if (isError) {
            toast.classList.add("error");
        }
        toast.textContent = message;
        document.body.appendChild(toast);

        toast.style.display = 'block';
        toast.style.opacity = '1';

        setTimeout(function() {
            toast.style.opacity = '0';
            setTimeout(function() {
                toast.remove();
            }, 600);
        }, 500);
    }

    document.querySelectorAll('input[type=number][name=quantity]').forEach(input => {
        input.addEventListener('change', function (){
            var max = parseInt(this.max, 10);
            var value = parseInt(this.value, 10);

            if(value > max){
                alert("The quantity cannot exceed the stock");
                this.value = max;
            } else if (value <= 0) {
                this.value = 0;
            }
        });
    });
</script>

<%@include file="footer.jsp"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
<style>
    .toast-container {
        position: fixed;
        top: 30%;
        left: 50%;
        transform: translate(-50%, -30%);
        z-index: 5000;
    }

    .toast-message {
        background-color: #A52A2A;
        color: white;
        padding: 20px 40px;
        border-radius: 10px;
        min-width: 300px;
        text-align: center;
        font-size: 20px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        position: fixed;
        top: 30%;
        left: 50%;
        transform: translate(-50%, -30%);
        display: none;
        opacity: 1;
        transition: opacity 0.5s ease;
        z-index: 10000;
    }

    .toast-message i {
        font-size: 25px;
    }

    .toast-message.error {
        background-color: #d9534f;
    }

    .toast-show {
        animation: fadeInOut 5s forwards;
    }

    @keyframes fadeInOut {
        0%, 100% { opacity: 0; visibility: hidden; }
        10%, 90% { opacity: 1; visibility: visible; }
    }

    .out-of-stock {
        background-color: red;
        color: white;
        cursor: not-allowed;
    }
</style>
</html>