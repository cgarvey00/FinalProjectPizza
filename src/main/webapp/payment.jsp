<%@ page import="com.finalprojectcoffee.entities.User" %>
<%@ page import="com.finalprojectcoffee.entities.Address" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.finalprojectcoffee.entities.Carts" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

    List<Address> addressList = (List<Address>) request.getSession().getAttribute("addressList");

    List<Carts> cartList = (List<Carts>) request.getSession().getAttribute("cartList");

    double total = 0;

    for (Carts cart : cartList) {
        total += cart.getTotalCost();
    }
    //Checking to ensure the User is logged in or not
    if (request.getSession(false) == null || loggedInUser == null || !"Customer".equals(loggedInUser.getUserType())) {
        response.sendRedirect("index.jsp");
    }

%>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
</head>
<style>
    :root {
        --main-color: #2980b9;
        --orange: #f39c12;
        --red: #e74c3c;
        --black: #333;
        --white: #fff;
        --light-color: #666;
        --light-bg: #eee;
        --border: .2rem solid var(--black);
        --box-shadow: 0 .5rem 1rem rgba(0, 0, 0, .1);
    }

    .home {
        background: -webkit-gradient(linear, left top, left bottom, from(rgba(0, 0, 0, 0.7)), to(rgba(0, 0, 0, 0.7))), url('${pageContext.request.contextPath}/images/homebg.jpg') no-repeat;
        background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url('${pageContext.request.contextPath}/images/homebg.jpg') no-repeat;
        background-size: cover;
        background-position: center;
        background-attachment: fixed;
    }


</style>
<body>
<%@include file="customer-nav.jsp" %>
<section class="checkout-orders">

    <form action="controller" method="POST">

        <h3>Your Orders</h3>

        <div class="display-orders">
            <div class="grand-total">Total : <span>&euro;<%=String.format("%.2f", total) %></span></div>
        </div>

        <h3>Complete Your Orders</h3>

        <div class="flex">

            <div class="inputBox">
                <span>Select Address :</span>
                <select name="address" class="box" required>
                    <%
                        for (Address address : addressList) { %>
                    <option value="<%= address.getId() %>"> Eircode: <%=address.getEirCode()%>
                        Town: <%=address.getTown()%>  Street: <%=address.getStreet()%> County: <%=address.getCounty()%>
                    </option>
                    <% }%>
                </select>
            </div>
            <div class="inputBox">
                <span>Payment Method :</span>
                <select name="method" class="box" required>

                    <option disabled value="In Cash">Cash</option>
                    <option disabled value="Paypal">PayPal</option>
                    <option disabled value="Revolut">Revolut</option>
                    <option value="Credit Cart">Credit Card</option>
                </select>
            </div>
            <div class="inputBox">
                <span>Card Number :</span>
                <input type="text" name="cardNumber" placeholder="3455 1234 8976 2212" class="box"
                       pattern="\d{4}\s?\d{4}\s?\d{4}\s?\d{4}" maxlength="19" required>
            </div>

            <div class="inputBox">
                <span>Expiry Date :</span>
                <input type="text" name="expdate" placeholder="MM/YY" class="box" maxlength="5"
                       pattern="(0[1-9]|1[0-2])\/\d{2}" required>
            </div>
            <div class="inputBox">
                <span>CVV :</span>
                <input type="password" name="cvv" placeholder="123" class="box" maxlength="3" pattern="\d{3}" required>
            </div>

        </div>
        <input type='hidden' name='action' value='add-order'>
        <input type='hidden' name='total' value='<%=String.format("%.2f", total) %>'>
        <input type="submit" name="add-order" class="btn <%=(total > 1) ? "" : "disabled" %>" value="place order">

    </form>

</section>

<script>


</script>

<%@include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>
</body>

</html>