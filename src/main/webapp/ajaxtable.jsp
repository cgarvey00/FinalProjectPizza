<%@ page import="com.finalprojectcoffee.entities.OrdersItem" %>
<%@ page import="java.util.List" %>

<center><section class="products" style="padding-top: 0; min-height:100vh;">
  <div class="box-container">
    <%
      List<OrdersItem> orderDetailList = (List<com.finalprojectcoffee.entities.OrdersItem>) request.getSession().getAttribute("orderDetailList");
      double orderTotal = 0;
      if (orderDetailList != null && !orderDetailList.isEmpty()) {
        for (OrdersItem orderDet : orderDetailList) { %>
    <div class="box bg-light">
      <div class="image">
        <img src="${pageContext.request.contextPath}/uploaded-images/<%=orderDet.getProduct().getImage()%>" width="100px; "height="100px;" alt="image">
      </div>
      <div class="content text-dark">
        <div class="text-dark" name="name">
          <%=orderDet.getProduct().getName()  %>
        </div>
        <div class="text-dark" style="font-size:10px;">
          <%=orderDet.getProduct().getDetails()  %>
        </div>
        <div class="price">
          <p class="text-dark" step="0.01">&euro;
            <%=String.format("%.2f", orderDet.getCost())  %>
          </p>
        </div>
        <h4 class="text-dark">Quantity</h4>
        <div class="content text-dark">
          <div class="text-dark" name="name">
            <%=orderDet.getQuantity()  %>
          </div>
        </div>
      </div>
    </div>
    <%
        orderTotal += orderDet.getCost();
      } %>
  </div>
  <% } else { %>
  <p class="empty">No Order Items Available </p>
  <% } %>
  </div>

</section></center>