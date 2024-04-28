<%@ page import="java.util.List" %>
<%@ page import="com.finalprojectcoffee.entities.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

    List<Order> freeOrders = (List<Order>) request.getSession().getAttribute("freeOrders");

    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || loggedInUser == null || !"Employee".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }
    session.setAttribute("loggedInUser", loggedInUser);

%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <!-- AJAX Link -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>

<body>
<%@include file="employee-nav.jsp" %>
<section class="products" style="padding-top: 0; min-height:100vh;">
    <br><br><br><br><br><br><br><br><br>
    <div class="box-container">
        <h2 class="heading">Orders To Deliver</h2>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Order ID</th>
                <th>Customer Name</th>
                <th>Total Amount</th>
                <th>Address</th>
                <th>EirCode</th>
                <th>View Details</th>
            </tr>
            </thead>
            <tbody>
            <% if (freeOrders != null && !freeOrders.isEmpty()) {
                for (Order order : freeOrders) {%>
            <tr>
                <td><%=order.getId()%>
                </td>
                <td><%=order.getCustomer().getUsername()%>
                </td>
                <td><%=order.getBalance()%>
                </td>
                <td>
                    <form action="controller" method="POST">
                        <input type="hidden" name="addressId" value="<%=order.getAddress()%>">
                        <button type="submit" class="btn btn-primary" name="action" value="emp-view-address">Address</button>
                    </form>
                </td>
                <td>
                    <form action="controller" method="POST">
                        <input type="hidden" name="orderId" value="<%=order.getId()%>">
                        <button type="submit" class="btn btn-primary" name="action" value="accept-order">Accept</button>
                    </form>
                </td>
                <td>
                    <button data-id="<%=order.getId()%>" class="orderinfo btn btn-success">Order Info</button>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="5">No Orders Are Free</td>
            </tr>
            <%} %>
            </tbody>
        </table>
    </div>
</section>
<%--Order Details From AJAX--%>
<div class="modal fade" id="orderDetails" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Order Details</h4>
                <button type="button" class="close" data-dismiss="modal">x</button>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/scripts/order-details.js" type="text/javascript"></script>
<%@include file="footer.jsp" %>

<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>

</body>
</html>
