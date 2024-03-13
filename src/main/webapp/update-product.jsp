<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page import="com.finalprojectcoffee.entities.Order" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
    Product product = (Product) request.getSession().getAttribute("product");


    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || loggedInUser == null || !"Admin".equals(loggedInUser.getUserType())) {
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>

<body>
<%@include file="admin-nav.jsp" %>
<section class="add-products">
    </br>
    </br>
    </br>
    </br>
    </br>
    </br>
    </br>
    <h1 class="heading">update product</h1>

    <form action="controller" method="post" >
        <input type="hidden" name="action" value="update-product">
        <input type="hidden" name="product_id" value="<%=product.getId()%>">
        <input type="hidden" name="old_image" value="<%=product.getImage()%>">
        <div class="image-container">
            <div class="main-image">
                <center> <img src="../uploaded-images/<%=product.getImage()%>" alt=""style="height:300px;"> </center>
            </div>
        </div>
        <span>Update Name</span>
        <input type="text" name="name" required class="box" maxlength="100" placeholder="enter product name"
               value="<%=product.getName()%>">
        <span>Update Category</span>
        <div class="inputBox">
            <select name="category" class="box" required>
                <option value="<%= product.getCategory() %>" selected ><%= product.getCategory() %></option>
                <option value="Sides">Sides</option>
                <option value="Meal_Deals">Meal Deals</option>
                <option value="Pizzas">Pizzas</option>
                <option value="Drinks">Drinks</option>
                <option value="Desserts">Desserts</option>
            </select>
        </div>
        <span>update price</span>
        <input type="number"  step="0.01" name="price" required class="box" min="0" max="9999999999"
               placeholder="enter product price" onkeypress="if(this.value.length == 10) return false;"
               value="<%=product.getPrice()%>">
        <span>update Quantity</span>
        <input type="number" name="stock" required class="box" min="0" max="9999999999"
               placeholder="enter product quantity" onkeypress="if(this.value.length == 1000) return false;"
               value="<%=product.getStock()%>">
        <span>update details</span>
        <textarea name="details" class="box" required cols="30" rows="10"><%=product.getDetails()%></textarea>
        <span>update image </span>
        <input type="text" name="image" required class="box" maxlength="100" placeholder="enter product image"
               value="<%=product.getImage()%>">
        <%--        <input type="file" name="image" accept="image/jpg, image/jpeg, image/png, image/webp" class="box">--%>
        <div class="flex-btn">
            <input type="submit" name="update-product" class="btn" value="update">
            <a href="controller?action=view-stock" class="option-btn">go back</a>
        </div>
    </form>

</section>
<%@include file="footer.jsp" %>
<script>
    <%
    Boolean addProductSuccess= (Boolean)request.getSession().getAttribute("add-product-success");
    String addProductError=(String) request.getSession().getAttribute("ape-message");

    if(addProductSuccess !=null){ %>
    <% if(addProductSuccess ){ %>
    alert("Product has been added Successfully!");
    <% }else if(addProductError !=null)  {%>
    alert("Failed to add Product has been added Successfully!");
    <%} %>
    <%} %>
    <% request.getSession().removeAttribute("add-product-success");
        request.getSession().removeAttribute("ape-message"); %>

</script>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>

</html>

