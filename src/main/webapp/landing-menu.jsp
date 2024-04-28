<%@ page import="com.finalprojectcoffee.entities.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Menu</title>

  <!-- font awesome cdn link  -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
  <link rel="icon" type="image/x-icon" href='${pageContext.request.contextPath}/uploaded-images/favicon.ico'>
  <!-- bootstrap cdn link  -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
  <!-- styles css link  -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>
<style>

</style>
<body>
<%@include file="normal-nav.jsp"%>
<!-- home section starts  -->
<br><br><br><br><br><br><br><br><br><br><br><br>
<div class="show-products" id="products">
  <h1 style="text-align: center;">View Products On Menu</h1>
  <div class="box-container">
    <%
      List<Product> productList = (List<Product>) request.getSession().getAttribute("productList");
      if (productList != null && !productList.isEmpty()) {
        for (Product p : productList) { %>
    <%--    <form action="controller" method="post">--%>
    <%--      <input type="hidden" name="action" value="landing-view-calories">--%>
    <%--      <input type="hidden" name="productId" value=<%=p.getId()%>>--%>
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
        <% if (p.getStock() <= 0) { %>
        <h4 style="font-weight: bold;"class="text-red">Out Of Stock</h4>
        <button type="submit" disabled name="view-product-customer" style="background-color:white;" class="cart-btn">View Item</button>
        <% } else {%>
        <button data-id="<%=p.getId()%>" style="background-color: #f3804e;"
                class="caloriesinfo btn btn-success">View Calories
        </button>
        <% }%>

      </div>
    </div>

    <% } %>
  </div>
  <% } else { %>
  <p class="empty">No Products Added Yet!</p>
  <% } %>
</div>
<div class="modal fade" id="caloriesDetails" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">Calories</h4>
        <button type="button" class="close" data-dismiss="modal">x</button>
      </div>
      <div class="modal-body" style="height: 10px;">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<%@include file="footer.jsp"%>
<script src="${pageContext.request.contextPath}/scripts/calories-view-details.js" type="text/javascript"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>
</html>