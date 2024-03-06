<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
    </br>  <br>
    <br>
    <br>
    <h1 class="heading">Add Product</h1>
    <form action="controller" method="POST">
        <input type="hidden" name="action" value="add-product">
        <div class="flex">
            <div class="inputBox">
                <span>Product Name (required)</span>
                <input type="text" class="box" required maxlength="100" placeholder="enter product name" name="name">
            </div>
            <div class="inputBox">
                <span>Product Price (required)</span>
                <input type="number" min="0" class="box" required max="9999999999" placeholder="Enter product price"
                       onkeypress="if(this.value.length == 10) return false;" name="price">
            </div>
            <div class="inputBox">
                <span>Quantity (required)</span>
                <input type="number" min="0" class="box" required max="9999999999" placeholder="Enter the Stock"
                       onkeypress="if(this.value.length == 10) return false;" name="stock">
            </div>
            <div class="inputBox">
                <span>Image</span>
                <textarea name="image" type="text" placeholder="Enter Product Image" class="box" required
                          maxlength="500"
                          cols="30" rows="10"></textarea>
            </div>
            <div class="inputBox">
                <span>Product Details (required)</span>
                <textarea name="details" type="text" placeholder="Enter Product Details" class="box" required
                          maxlength="500"
                          cols="30" rows="10"></textarea>
            </div>
            <div class="inputBox">
                <span>Category</span>
                <select name="category" class="box" required>
                    <option value="" selected disabled>Select category</option>
                    <option value="Sides">Sides</option>
                    <option value="Meal_Deals">Meal Deals</option>
                    <option value="Pizzas">Pizzas</option>
                    <option value="Drinks">Drinks</option>
                    <option value="Desserts">Desserts</option>
                </select>
            </div>
        </div>
        <input type="submit" value="add product" class="btn" name="add-product">
    </form>

</section>

<section class="show-products">
    </br>
    </br>
    </br>
    </br>

    <h1 class="heading">Products </h1>

    <div class="box-container">

        <%
            List<Product> productList = (List<Product>) request.getSession().getAttribute("productList");
            if (productList != null && !productList.isEmpty()) {
                for (Product p : productList) { %>
        <div class="box">
            <form action="controller" method='POST'>
                <input type='hidden' name='product_id' value='<%= p.getId() %>'>

                <img src="../uploaded-images/<%= p.getImage() %>" alt="">
                <div class="name">
                    <%= p.getName() %>
                </div>
                <div class="details">Category: <span>
                        <%= p.getCategory() %>
                </div>
                <div class="price">&euro;<span>
                         <%= p.getPrice() %>
                        </span>
                </div>
                <div class="details">Details: <span>
                          <%= p.getDetails() %>
                        </span>
                </div>
                <div class="stock">Stock: <span>
                            <%= p.getStock() %>
                        </span>
                </div>
                <div class="flex-btn">
                    <input type='hidden' name='action' value='view-update-product'>

                    <button type="submit" name="view-update-product" class="option-btn">Update</button>
            </form>
            <form action="controller" method='POST'>
                <input type='hidden' name='product_id' value='<%=p.getId() %>'>
                <input type='hidden' name='action' value='delete-product'>
                <button type="submit" name="delete-product" class="delete-btn"
                        onclick="return confirm('delete this product?');">delete
                </button>
            </form>
        </div>
    </div>
    <% } %>
    </div>
    <% } else { %>
    <p class="empty">No Products Added Yet!</p>
    <% } %>
    <%--    </div>--%>

</section>
<%@include file="footer.jsp" %>
<script>
    <%
    Boolean addProductSuccess= (Boolean)request.getSession().getAttribute("add-product-success");
    String addProductError=(String) request.getSession().getAttribute("ape-message");
    Boolean updateProductSuccess= (Boolean)request.getSession().getAttribute("update-product-success");
    String updateProductError=(String) request.getSession().getAttribute("pue-message");
    Boolean deleteProductSuccess= (Boolean)request.getSession().getAttribute("delete-product-success");
    String deleteProductError=(String) request.getSession().getAttribute("pde-message");


       if(addProductSuccess !=null){ %>
    <% if(addProductSuccess ){ %>
    alert("Product has been added Successfully!");
    <% }else if(addProductError !=null)  {%>
    alert("Failure to add Product ");
    <%} %>
    <%} %>


    <% if(updateProductSuccess !=null){ %>
    <% if(updateProductSuccess ){ %>
    alert("Product has been updated Successfully!");
    <% }else if(updateProductError !=null)  {%>
    alert("Failed to Update Product ");
    <%} %>
    <%} %>

    <% if(deleteProductSuccess !=null){ %>
    <% if(deleteProductSuccess ){ %>
    alert("Product has been Deleted Successfully!");
    <% }else if(deleteProductError !=null)  {%>
    alert("Failed to Delete Product ");
    <%} %>
    <%} %>


    <% request.getSession().removeAttribute("update-product-success");
        request.getSession().removeAttribute("ape-message"); %>
    <% request.getSession().removeAttribute("add-product-success");
        request.getSession().removeAttribute("pue-message"); %>
    <% request.getSession().removeAttribute("delete-product-success");
     request.getSession().removeAttribute("pde-message"); %>


</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>

</html>
