<%@ page import="java.util.List" %>
<%@ page import="com.finalprojectcoffee.entities.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

    List<Order> employeeOrders = (List<Order>) request.getSession().getAttribute("orderListEmployee");

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
    <title>Employee Order List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <!-- AJAX Link -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <link rel="icon" type="image/x-icon" href='${pageContext.request.contextPath}/uploaded-images/favicon.ico'>
</head>
<style>

    .alert {
        padding: 15px;
        margin-bottom: 20px;
        border: 1px solid transparent;
        border-radius: 4px;
        color: #a94442;
        background-color: #f2dede;
        border-color: #ebccd1;
    }


    .success {
        padding: 15px;
        margin-bottom: 20px;
        border: 1px solid transparent;
        border-radius: 4px;
        color: #27942b;
        background-color: #affdb2;
        border-color: #b0ff93;
    }
</style>
<body>
<%@include file="employee-nav.jsp" %>orderAccepted
<section class="products" style="padding-top: 0; min-height:100vh;">
    <br><br><br><br><br><br><br><br><br>

    <div class="box-container">
        <h2 class="heading">Orders To Be Delivered</h2>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Customer Name</th>
                <th>Total Amount</th>
                <th>Address</th>
                <th>View Details</th>
                <th>Deliver Order</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <% if (employeeOrders != null && !employeeOrders.isEmpty()) {
                for (Order order : employeeOrders) {
                    if (order.getStatus().equals(Status.Delivering)) {
            %>
            <tr>
                <td><%=order.getCustomer().getUsername()%>
                </td>
                <td>&euro;<%=String.format("%.2f", order.getBalance()) %>
                </td>
                <td>
                    <button data-id="<%=order.getAddress().getId()%>" style="background-color: #00ccff;"
                            class="addressInfo btn btn-primary">Address
                    </button>
                </td>
                <td>
                    <button data-id="<%=order.getId()%>" style="background-color: #4caf50;"
                            class="orderinfo btn btn-success">Order Info
                    </button>
                </td>
                <td>
                    <form action="controller" method="POST">
                        <input type="hidden" name="orderId" value="<%=order.getId()%>">
                        <button type="submit" style="background-color: #f39c12;" class="btn btn-warning" name="action"
                                value="finish-order">Finish Order
                        </button>
                    </form>
                </td>
                <td>
                    <%=order.getStatus()%>
                </td>
            </tr>
            <%} else { %>
            <td colspan="5">No Orders are being collected yet!</td>
            <% }
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

<%--Address Details From AJAX--%>
<div class="modal fade" id="addressDetails" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Address Details</h4>
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
<script>
    $('body').on('click', '.addressInfo', function () {
        var addressId = $(this).data('id');
        $.ajax({
            url: 'controller?action=emp-view-address2',
            type: 'POST',
            data: {addressId: addressId},
            success: function (response) {
                $('#addressDetails .modal-body').html(response);
                $('#addressDetails ').modal('show');
            }
        });
    });
    // });
</script>
<%@include file="footer.jsp" %>

<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>

</body>
</html>
