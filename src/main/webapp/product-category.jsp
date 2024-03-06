<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>View Category</title>

  <!-- font awesome cdn link  -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

  <!-- bootstrap cdn link  -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
  <!-- styles css link  -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/customer.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>
<body>
<%@include file="customer-nav.jsp"%>
<section class="search-form">
  <h3>Search for a Product Here...</h3>
  <form action="controller" method="POST">
    <input type="hidden" name="action" value="view-products-category">
    <div class="form-outline form-white mb-4">
      <label style="color:#fff;" for="usertype">User Type</label>
      <select name="category" id="usertype" class="required form-control form-control-lg"
              onchange="enableSubmit()">
        <option value="Sides">Sides</option>
        <option value="Pizzas">Pizza </option>
        <option value="Meal_Deals">Meal Deals</option>
        <option value="Drinks">Drinks</option>
        <option value="Desserts">Desserts</option>
      </select>
    </div>
    <button type="submit" class="fas fa-search" name="view-products-category"></button>
  </form>
</section>
<section class="products" style="padding-top: 0; min-height:100vh;">

  <div class="box-container">

    <%
      List<Product> productList = (List<Product>) request.getSession().getAttribute("productList");
      if (productList != null && !productList.isEmpty()) {
        for (Product p : productList) { %>
    <form action="." method="post">
      <input type="hidden" name="action" value="add-to-cart">
      <input type="hidden" name="product_id" value=<%=p.getId()%>>
      <input type="hidden" name="name" value=<%=p.getName()%>>
      <input type="hidden" name="price" value=<%=p.getPrice()%>>
      <div class="box bg-light">
        <div class="image">
          <img src="${pageContext.request.contextPath}/uploaded-images/<%= p.getImage() %>" name="image" alt="image">
        </div>
        <div class="content text-dark">
          <h3 class="text-dark" name="name">
            <%=p.getName()  %>
          </h3>
          <h3 class="text-dark" style="font-size:10px;">
            <%=p.getDetails()  %>
          </h3>
          <div class="price">
            <p class="text-dark" step="0.01">&euro;
              <%=String.format("%.2f", p.getPrice())  %>
            </p>
          </div>
          <h4 class="text-dark">Place cart quantity</h4>
          <label>
            <input type="number" name="qty" style="font-size:15px;" required class="box" min="0" max="40" placeholder="enter quantity" value="1">
          </label>
          <button type="submit" name="add-to-cart" class="cart-btn">Add to Cart</button>

        </div>
      </div>
    </form>
    <% } %>
  </div>
  <% } else { %>
  <p class="empty">No Products Present... Try another Keyword</p>
  <% } %>
  </div>

</section>

<%@include file="footer.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
</html>
